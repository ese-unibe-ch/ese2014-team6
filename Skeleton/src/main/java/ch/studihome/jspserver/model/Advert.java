package ch.studihome.jspserver.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;

/**
 * Generic advert object. Can be for shared flats or apartments.
 *  
 * @author TeamSix
 *
 */


@Entity
public class Advert {
	
	@Id
	@GeneratedValue
	private Long adv_id;
	
	private String title;
	private int price;
	private boolean isWG;
	private int appartementSize;	//in square meters
	private int numberOfRooms;
	private int roomSize;	//in square meters, only for WG
	private int numberOfInhabitants;	//only for WG
	
	@Column(columnDefinition="character varying (2048) not null")
	private String description;
	
	@ManyToOne @JoinColumn(name="usr_id", nullable=false)
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="advert")
	private Address address;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="advert")
	private Set<RoomImg> imgs = new HashSet<RoomImg>(0);
	
	public Advert() {}
	// TODO: Add images in constructor
	public Advert(
			String title, int price, boolean isWG, int appartementSize, int numberOfRooms, 
			int roomSize, int numberOfInhabitants, String description, User user, Address address
			) {
		this.title = title;
		this.price = price;
		this.isWG = isWG;
		this.numberOfRooms = numberOfRooms;
		this.roomSize = roomSize;
		this.numberOfInhabitants = numberOfInhabitants;
		this.description = description;
		this.user = user;
		this.address = address;
	}
	
	// To get image name from jsp-file
	public String getFirstImage() {
		return this.getImgs().iterator().next().getImgName();

	}
	public Long getAdv_id() {
		return adv_id;
	}
	
	public void setAdv_id(Long id) {
		this.adv_id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<RoomImg> getImgs() {
		return imgs;
	}

	public void setImgs(Set<RoomImg> imgs) {
		this.imgs = imgs;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getIsWG() {
		return isWG;
	}

	public void setIsWG(boolean isWG) {
		this.isWG = isWG;
	}

	public int getAppartementSize() {
		return appartementSize;
	}

	public void setAppartementSize(int appartementSize) {
		this.appartementSize = appartementSize;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(int roomSize) {
		this.roomSize = roomSize;
	}

	public int getNumberOfInhabitants() {
		return numberOfInhabitants;
	}

	public void setNumberOfInhabitants(int numberOfInhabitants) {
		this.numberOfInhabitants = numberOfInhabitants;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adv_id == null) ? 0 : adv_id.hashCode());
		result = prime * result + appartementSize;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isWG ? 1231 : 1237);
		result = prime * result + numberOfInhabitants;
		result = prime * result + numberOfRooms;
		result = prime * result + price;
		result = prime * result + roomSize;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Advert other = (Advert) obj;
		if (adv_id == null)
		{
			if (other.adv_id != null)
				return false;
		} else if (!adv_id.equals(other.adv_id))
			return false;
		if (appartementSize != other.appartementSize)
			return false;
		if (description == null)
		{
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (isWG != other.isWG)
			return false;
		if (numberOfInhabitants != other.numberOfInhabitants)
			return false;
		if (numberOfRooms != other.numberOfRooms)
			return false;
		if (price != other.price)
			return false;
		if (roomSize != other.roomSize)
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	
}
