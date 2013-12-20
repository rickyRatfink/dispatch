package org.dispatch.faithfarm.struts.action;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.dispatch.faithfarm.domain.Constants;
import org.dispatch.faithfarm.domain.ErrorMessage;
import org.dispatch.faithfarm.domain.SystemUser;
import org.dispatch.faithfarm.hibernate.data.SystemUserDao;
import org.dispatch.faithfarm.struts.form.LoginForm;
import org.dispatch.faithfarm.utils.HtmlDropDownBuilder;
import org.dispatch.faithfarm.utils.Validator;


public class LoginAction extends Action {
	
	private final static Logger LOGGER = Logger.getLogger(LoginAction.class.getName());
	private final static HtmlDropDownBuilder html = new HtmlDropDownBuilder();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {		
		LOGGER.setLevel(Level.SEVERE);

		 HttpSession session = request.getSession(true);
		 String action = request.getParameter("action");
		 LoginForm loginForm = (LoginForm)form;
		 

		String ip=request.getRemoteAddr().toString();
		if (!"75.147.217.62".equals(ip) && //Boynton Beach Farm
				!"70.89.102.41".equals(ip) && //FTL Farm
				!"127.0.0.1".equals(ip)  ) { //Local Development Box
			LOGGER.log(Level.SEVERE,"INVALID IP ADDRESS TRIED TO ACCESS THE SYSTEM: "+request.getRemoteAddr().toString());
			return mapping.findForward(Constants.ACCESS_DENIED);
		}
		 
		 if ("logout".equals(action)) {
			 loginForm.setSystemUser(null);
			 session.invalidate();
			 return mapping.findForward(Constants.LOGOUT);
		 }
		 //SystemUserHome userDao = new SystemUserHome();
		 SystemUserDao userDao = new SystemUserDao();
		 //UserAuthorizedSessionDao sessionDao = new UserAuthorizedSessionDao();
		 //UserAuthorizedSession sessionObj = new UserAuthorizedSession();
		 
		 ActionRedirect redirect = null;
		 
		 LOGGER.log(Level.INFO, "In login action..."+loginForm.getSystemUser().getUsername());
		 SystemUser user=null;
		
		 if ("Login".equals(action)) {
			 session.setAttribute("error",null);
			 boolean valid = this.validate(loginForm);
			 if (!valid)
				 return mapping.findForward(Constants.LOGIN);
			 
			 user = userDao.authenticate(loginForm.getUsername(), loginForm.getPassword());
			 
			 if (user!=null) {
				 if ("Dispatch".equals(user.getGroup_())) {
					 loginForm.setSystemUser(user);
					 session.setAttribute("USER_"+session.getId(), user);
					 html.refresh(session);
						
					 return mapping.findForward(Constants.SUCCESS);
				 } else {
					 loginForm.setErrorMessage("Access denied. This user is not authorized to view this application.");
					return mapping.findForward(Constants.LOGIN);
				 }
			 } else
			 {
				 loginForm.setUsername(null);
				 loginForm.setPassword(null);
				 return mapping.findForward(Constants.FAILURE);
			 }
			 
		 }
		
		 return mapping.findForward(Constants.LOGIN);
		 //return mapping.findForward(Constants.GLOBAL_LOGIN);
	}
	
	public boolean validate(LoginForm loginForm) {
		  String msg="";
		  
		  if ((loginForm.getUsername()==null) || (loginForm.getUsername().length() ==0)) 
		      msg="username is required";
		  if ((loginForm.getPassword()==null) || (loginForm.getPassword().length() ==0)) 
			  if (msg.length()>0)
				  msg="username and password is required";
			  else
				  msg="password is required";
		  
		  loginForm.setErrorMessage(msg);
		  
		  if (msg.length()>0) 
			  return false;
		  else
			  return true;
		}
	
}
