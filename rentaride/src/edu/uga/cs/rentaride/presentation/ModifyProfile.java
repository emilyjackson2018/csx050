package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
 import java.io.IOException;
 import java.io.OutputStreamWriter;
 import java.util.HashMap;
 import java.util.List;
 import java.util.ArrayList;
 import java.util.LinkedList;
 import java.util.Map;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.Enumeration;
 
 import java.text.SimpleDateFormat; 
 
 import javax.servlet.ServletException;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 
 import edu.uga.cs.rentaride.entity.Customer;
 import edu.uga.cs.rentaride.entity.Administrator;
 import edu.uga.cs.rentaride.entity.User;
 import edu.uga.cs.rentaride.entity.UserStatus;
 import edu.uga.cs.rentaride.RARException;
 import edu.uga.cs.rentaride.logic.LogicLayer;			//may need to be changed to find stuff
 import edu.uga.cs.rentaride.session.Session;
 import edu.uga.cs.rentaride.session.SessionManager;
 
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import freemarker.template.TemplateException;
 
 
 @WebServlet("ModifyProfile")
 public class ModifyProfile extends HttpServlet{
 	
 private static final long serialVersionUID = 1L;
     
     static  String  templateDir = "WEB-INF/templates";
     //static  
     String  resultTemplateName = "modifyProfile-result.ftl";
 
     private Configuration cfg; 
 
     public void init() {
         // Prepare the FreeMarker configuration;
         // - Load templates from the WEB-INF/templates directory of the Web app.
         cfg = new Configuration();
         cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
     }
     
     public void doGet( HttpServletRequest req, HttpServletResponse res )
             throws ServletException, IOException
     {
         Template       resultTemplate = null;
         Session        session;
         HttpSession    httpSession = null;
         
		 String		   newPassword = null; 
		 String 		firstName = null;
		 String 		lastName = null;
		 String 		email = null;
		 String 		address = null;
		 
		 // Customer specific parameters
		 List<Customer> userNameCheck = new ArrayList<Customer>();
		 String			userName = null;
		 String			licenseNumber = null;
		 String 		licenseState = null;
		 String			creditCardNumber = null;
		 String			cardExpiration = null;
		 String			status = null;
		 String			renew = null;
		 String			date = null;
		 Date 			newMemberUntil = null;
		 Date			cardExpire = null;
		 UserStatus		convStatus = null;
		 
         LogicLayer	   logicLayer = null;
         BufferedWriter toClient = null;
         String         ssid = null;
 
         
      // Load templates from the WEB-INF/templates directory of the Web app.
         //
         try {
             resultTemplate = cfg.getTemplate( resultTemplateName );
         } 
         catch (IOException e) {
			 String thrower = "Can't load template in: " +  templateDir +  ": " + e.toString();
             throw new ServletException( thrower );
                     
         }
 
         // Prepare the HTTP response:
         // - Use the charset of template for the output
         // - Use text/html MIME-type
         //
         toClient = new BufferedWriter(
                 new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() )
                 );
 
         res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
 
         httpSession = req.getSession();
         if( httpSession == null ) {       // assume not logged in!
             RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
             return;
         }
 
         ssid = (String) httpSession.getAttribute( "ssid" );
         if( ssid == null ) {       // not logged in!
         	RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
             return;
         }
 
         session = SessionManager.getSessionById( ssid );
         if( session == null ) {
         	RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
             return; 
         }
         
         logicLayer = session.getLogicLayer();
         if( logicLayer == null ) {
         	RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
             return; 
         }
 
         // Get the form parameters
         //
         newPassword = req.getParameter("password");
		 firstName = req.getParameter("firstName");
		 lastName = req.getParameter("lastName");
		 email = req.getParameter("email");
		 address = req.getParameter("address");
		 
		 

		if(session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.CustomerImpl") {
		 userName = req.getParameter("userName");
		 licenseNumber = req.getParameter("licenseNumber");
		 licenseState = req.getParameter("licenseState");
		 creditCardNumber = req.getParameter("creditCardNumber");
		 cardExpiration = req.getParameter("cardExpiration");
		 status = req.getParameter("status");
		 renew = req.getParameter("renew");
		}
		 
		 if( newPassword == "" && firstName == "" && lastName == "" && email == "" && address == "" ) {
			 if (((userName == "" && licenseNumber == "" && licenseState == "" && creditCardNumber == "" && cardExpiration == "") 
					&& session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.CustomerImpl") || 
					session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.AdministratorImpl")
             RARError.error( cfg, toClient, "No input given for profile modification" );
             return;
         }
		 if( newPassword != "" ) {
         	session.getUser().setPassword(newPassword);
         }
		 if( firstName != "" ) {
         	session.getUser().setFirstName(firstName);
         }
		 if( lastName != "" ) {
         	session.getUser().setLastName(lastName);
         }
		 if( email != "" ) {
         	session.getUser().setEmail(email);
         }
		 if( address != "" ) {
         	session.getUser().setAddress(address);
         }
                  
         
         if (session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.AdministratorImpl") {
         	Administrator admin = (Administrator) session.getUser();
         	try {
         		logicLayer.modifyAdmin(admin);
         	} catch (RARException e) {
         		e.printStackTrace();
         	}
         }
         if (session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.CustomerImpl") {
         	//CHECK IF USERNAME UNIQUE (NEED LOGIC LAYER FUNCTION FOR THIS?) OTHERWISE ERROR
			userNameCheck = logicLayer.AllCustomerInfo();
			Iterator itr = userNameCheck.iterator();
			while (itr.hasNext()) {
				Customer current = (Customer) itr.next();
				if (current.getUserName() == userName) RARError.error( cfg, toClient, "Username input not unique (another customer has this username)" );
			}
			
			if( userName != "" ) {
				session.getUser().setUserName(userName);
			}
			Customer customer = (Customer) session.getUser();
			if( licenseNumber != "" ) {
				customer.setLicenseNumber(licenseNumber);
			}
			if( licenseState != "" ) {
				customer.setLicenseState(licenseState);
			}
			if( creditCardNumber != "" ) {
				customer.setCreditCardNumber(creditCardNumber);
			}
			if( cardExpiration != "" ) {
				cardExpire = new SimpleDateFormat("dd/MM/yyyy").parse(cardExpiration);;
				customer.setCreditCardExpiration(cardExpire);
			}
			if( status != "") {
				convStatus = UserStatus.valueOf(status);
				customer.setUserStatus(convStatus);
			}
			if( Boolean.valueOf(renew) ) {
				
				newMemberUntil = new Date();
				customer.setMemberUntil(newMemberUntil);
			}
         	try {
         		logicLayer.modifyCustomer(customer);
         	} catch (RARException e) {
         		e.printStackTrace();
         	}
         }
 
         
         // Setup the data-model
         //
         Map<String,Object> root = new HashMap<String,Object>();
 
         // Build the data-model
         //
         root.put( "message", "Thank you for resetting your password!");
         try {
             resultTemplate.process( root, toClient );
             toClient.flush();
         } 
         catch (TemplateException e) {
             throw new ServletException( "Error while processing FreeMarker template", e);
         }
 
         toClient.close();
         
     }   
 	
 }