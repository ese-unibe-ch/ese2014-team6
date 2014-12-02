package ch.studihome.jspserver.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.studihome.jspserver.controller.exceptions.InvalidUserException;
import ch.studihome.jspserver.controller.service.AdService;
import ch.studihome.jspserver.controller.service.InviteService;
import ch.studihome.jspserver.controller.service.MessageService;
import ch.studihome.jspserver.controller.service.SignupService;
import ch.studihome.jspserver.model.Advert;
import ch.studihome.jspserver.model.Calendar;
import ch.studihome.jspserver.model.Event;
import ch.studihome.jspserver.model.Message;
import ch.studihome.jspserver.model.User;
import ch.studihome.jspserver.model.dao.AdvertDao;
import ch.studihome.jspserver.model.dao.InviteDao;
import ch.studihome.jspserver.model.dao.MessageDao;
import ch.studihome.jspserver.model.dao.UserDao;
import ch.studihome.jspserver.model.pojos.AdForm;
import ch.studihome.jspserver.model.pojos.BSalert;
import ch.studihome.jspserver.model.pojos.InvitationForm;
import ch.studihome.jspserver.model.pojos.MessageForm;

/**
 * Handle message editing and sending
 * 
 * @author TeamSix
 */
@Controller
public class InviteController {

    @Autowired InviteDao inviteDao;
    @Autowired InviteService inviteService;
    @Autowired AdvertDao advertDao;
    @Autowired UserDao userDao;
    
	static Logger log = Logger.getLogger(AdvertController.class.getName());
	
	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
    public ModelAndView invite(
    		@RequestParam(value = "usrId", required = false)Long usrId
    		) {
				
		ModelAndView model = new ModelAndView("test");
//		User fromUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		User toUser = userDao.findByUsrId(usrId);
		
        model.addObject("invitationForm", new InvitationForm());
//      model.addObject("fromUser", fromUser);
//		model.addObject("toUser", toUser);
		        
		return model;
    }
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ModelAndView getInvite(
			@Valid InvitationForm invitationForm,
			BindingResult result,
			RedirectAttributes redirectAttributes
			) {
		ModelAndView model = new ModelAndView("test");
		BSalert[] alerts = new BSalert[1];
		
		if (!result.hasErrors()) {
			try {
				invitationForm = inviteService.saveInvite(invitationForm);
				alerts[0] = new BSalert(BSalert.Type.success, "<strong>Success!</strong> Invitation send.");
		
			} catch (InvalidUserException e) {
            	alerts[0] = new BSalert(BSalert.Type.danger, "<strong>Error!</strong> " + e.getMessage());
			}
		} else {
//			TODO: Proper error handling
		}
		model.addObject("invitationForm", invitationForm);
		model.addObject("alerts", alerts);
		return model;
		
	}
}


