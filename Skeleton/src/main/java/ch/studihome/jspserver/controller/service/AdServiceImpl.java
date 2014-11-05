package ch.studihome.jspserver.controller.service;

import java.util.HashSet;
import java.util.Set;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ch.studihome.jspserver.controller.AdpageController;
import ch.studihome.jspserver.controller.exceptions.ImageSaveException;
import ch.studihome.jspserver.model.Address;
import ch.studihome.jspserver.model.Advert;
import ch.studihome.jspserver.model.RoomImg;
import ch.studihome.jspserver.model.User;
import ch.studihome.jspserver.model.dao.AddressDao;
import ch.studihome.jspserver.model.dao.AdvertDao;
import ch.studihome.jspserver.model.dao.UserDao;
import ch.studihome.jspserver.model.pojos.AdForm;

@Service
public class AdServiceImpl implements AdService {
	
	@Autowired    AdvertDao advertDao;
    @Autowired    AddressDao addrDao;
    @Autowired    UserDao usrDao;
	static Logger log = Logger.getLogger(AdpageController.class.getName());
    
    // Image location = imgPath + imageName
    @Value("${path.adimg}")
	private String imgPath;

    public Iterable<Advert> findAll() {
    	log.info("INFO: There are " + advertDao.count() + 
    			" adverts registered. Returning them all");
    	return advertDao.findAll();
    }
    
    @Transactional
	public AdForm loadById(String adId)
	{
		AdForm adForm = new AdForm();
		
		Advert ad = advertDao.findOne(Long.parseLong(adId));
		
		if(ad == null)
		{
			return null;
		}
		
		adForm.setId(ad.getAdv_id());
		adForm.setTitle(ad.getTitle());
		adForm.setPrice(Integer.toString(ad.getPrice()));
		adForm.setStreet(ad.getAddress().getStreet());
		adForm.setPlz(ad.getAddress().getPlz());
		adForm.setCity(ad.getAddress().getCity());
		adForm.setLatlng(ad.getAddress().getCoordinate());
		adForm.setIsWG(ad.isWG());
		adForm.setAppartementSize(ad.getAppartementSize());
		adForm.setNumberOfRooms(ad.getNumberOfRooms());
		adForm.setRoomSize(ad.getRoomSize());
		adForm.setNumberOfInhabitants(ad.getNumberOfInhabitants());
		adForm.setDescription(ad.getDescription());
		adForm.setOwnerId(ad.getUser().getUsr_id().toString());
		
		return adForm;
	}
	
    @Transactional(readOnly = false)
    public AdForm saveFrom(AdForm adForm) throws ImageSaveException
	{
    	User user = usrDao.findOne(Long.decode(adForm.getOwnerId()));
    	Advert[] adverts = new Advert[0];
    	adverts = user.getAds().toArray(adverts);
        Set<Advert> newset = new HashSet<Advert>(0);
    	
        Address address = new Address();
        address.setStreet(adForm.getStreet());
        address.setPlz(adForm.getPlz());
        address.setCity(adForm.getCity());
        address.setCoordinate(adForm.getLatlng());
        
        Advert ad = new Advert();
        
        ad.setAddress(address);
        address.setAdvert(ad);
        if(adForm.getId() != 0)
        {
        	ad.setAdv_id(adForm.getId());
        }
        ad.setTitle(adForm.getTitle());
        ad.setPrice(Integer.parseInt(adForm.getPrice()));
        ad.setWG(adForm.getIsWG());
        ad.setAppartementSize(adForm.getAppartementSize());
        ad.setNumberOfRooms(adForm.getNumberOfRooms());
        ad.setRoomSize(adForm.getRoomSize());
        ad.setNumberOfInhabitants(adForm.getNumberOfInhabitants());
        ad.setDescription(adForm.getDescription());
        ad.setUser(user);

        // Todo: Check if upload is an image. (eg "image" = image.getContentType().split("/")[0])

        try {
			//Save image to directory
			Integer imgNr = 1;
			MultipartFile image = adForm.getImage();
			log.info("INFO: File Content Type is " + image.getContentType());
//			TODO: Read Ad id correctly
//			String name = Objects.toString(ad.getId()) + 
//					"_" + Objects.toString(imgNr) + 
//					"." + image.getContentType().split("/")[1];
			String name = image.getOriginalFilename();
			String imagePath = imgPath + name;
			byte[] bytes = image.getBytes();
			BufferedOutputStream stream = 
					new BufferedOutputStream(
							new FileOutputStream(new File(imagePath)));
			stream.write(bytes);
			stream.close();
			
			// Save image name to db
			RoomImg img = new RoomImg();
			img.setAdvert(ad);
			img.setImgDescription("Temp description");
			img.setImgName(name);
			img.setImgNum(imgNr);
			Set<RoomImg> rset = new HashSet<RoomImg>(0);
			rset.add(img);
			ad.setImgs(rset);
		} catch (Exception e) {
			throw new ImageSaveException("Error while saving your image.\n" + e.toString());
		}
        
        for(Advert a: adverts)
        {
        	if(!a.getAdv_id().equals(ad.getAdv_id()))
        	{
        		newset.add(a);
        	}
        }
        newset.add(ad);
        user.setAds(newset);
        
		ad = advertDao.save(ad);	// save ad to DB (has to be done, to easily get the adId
        
        adForm.setId(ad.getAdv_id());
        
        user = usrDao.save(user);   // save user to DB

        return adForm;

    }
	
}
