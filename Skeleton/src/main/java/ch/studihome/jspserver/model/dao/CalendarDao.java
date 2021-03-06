package ch.studihome.jspserver.model.dao;

import org.springframework.data.repository.CrudRepository;

import ch.studihome.jspserver.model.Advert;
import ch.studihome.jspserver.model.Calendar;

public interface CalendarDao extends CrudRepository<Calendar,Long> {

	Calendar findByCalId(Long id);
	
}
