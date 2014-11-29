package ch.studihome.jspserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ch.studihome.jspserver.model.User;
import ch.studihome.jspserver.model.dao.UserDao;

/**
 * Load and return user profile view 
 * 
 * @author TeamSix
 */
@Controller
public class ProfileController {
	
	@Autowired
    UserDao usrDao;
	
	public ProfileController() {}
		
    public ProfileController(UserDao usrDao)
    {
		this.usrDao = usrDao;
	}

	/**
	 * 
	 * @return Profile view
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView index()
    {
    	ModelAndView model = new ModelAndView("profile");
    	
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	model.addObject("user", user);

    	return model;
    }

}


