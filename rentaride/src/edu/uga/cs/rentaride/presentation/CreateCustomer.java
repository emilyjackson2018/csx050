
package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class CreateCustomer
    extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    static  String         templateDir = "WEB-INF/templates";
    static  String         resultTemplateName = "CreateCustomer-Result.ftl";

    private Configuration  cfg; 

    public void init() 
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        //
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(
                getServletContext(), 
                "WEB-INF/templates"
                );
    }

    public void doPost( HttpServletRequest req, HttpServletResponse res )
            throws ServletException, IOException
    {
        Template       resultTemplate = null;
        BufferedWriter toClient = null;
        
		String firstName;
		String lastName;
		String userName;
		String password;
		String email;
		String address;
		String createDate;
		String memberUntil;
		String licState;
		String licNumber;
		String ccNumber;
		String ccExpiration;
		UserStatus status;
		long CustomerId = 0;
		
        LogicLayer     logicLayer = null;
        HttpSession    httpSession;
        Session        session;
        String         ssid;

        // Load templates from the WEB-INF/templates directory of the Web app.
        //
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        } 
        catch (IOException e) {
            throw new ServletException( 
                    "Can't load template in: " + templateDir + ": " + e.toString());
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
		 firstName = req.getParameter("firstName");
		 lastName = req.getParameter("lastName");
		 userName = req.getParameter("userName");
		 password = req.getParameter("password");
		 email = req.getParameter("email");
		 address = req.getParameter("address");
		 createDate = req.getParameter("createDate");
		 memberUntil = req.getParameter("memberUntil");
		 licState = req.getParameter("licState");
		 licNumber = req.getParameter("licNumber");
		 ccNumber = req.getParameter("ccNumber");
		 ccExpiration = req.getParameter("ccExpiration");
		 status = req.getParameter("status");
		 //id = req.getParameter("id");
   

        if( firstName == null || lastName == null) {
            RARError.error( cfg, toClient, "Unspecified first or last name" );
            return;
        }
		if( userName == null ) {
            RARError.error( cfg, toClient, "Unspecified username" );
            return;
        }
		if( password == null ) {
            RARError.error( cfg, toClient, "Unspecified password" );
            return;
        }
		if( email == null ) {
            RARError.error( cfg, toClient, "Unspecified email" );
            return;
        }
		if( address == null ) {
            RARError.error( cfg, toClient, "Unspecified address" );
            return;
        }
		if( createDate == null ) {
            RARError.error( cfg, toClient, "Unspecified date of creation" );
            return;
        }
		if( memberUntil == null ) {
            RARError.error( cfg, toClient, "Unspecified membership expiration" );
            return;
        }
		if( licState == null ) {
            RARError.error( cfg, toClient, "Unspecified license state" );
            return;
        }
		if( licNumber == null ) {
            RARError.error( cfg, toClient, "Unspecified license number" );
            return;
        }
	    if( ccNumber == null ) {
            RARError.error( cfg, toClient, "Unspecified credit card number" );
            return;
        }
		if( ccExpiration == null ) {
            RARError.error( cfg, toClient, "Unspecified credit card expiration date" );
            return;
        }
	
/*
        try {
            founder_id = Long.parseLong( person_id_str );
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "founder_id should be a number and is: " + person_id_str );
            return;
        }
		*/

        try {
            CustomerId = logicLayer.CreateCustomer(firstName,
						 lastName,
						 userName,
						 password,
						 email,
						 address,
						 createDate,
						 memberUntil,
						 licState,
						 licNumber,
						 ccNumber,
						 ccExpiration,
						 status);
        } 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        }

        // Setup the data-model
        //
        Map<String,Object> root = new HashMap<String,Object>();

        // Build the data-model
        //
        root.put( "firstName", firstName );
		root.put( "lastName", lastName );
        root.put( "CustomerId", new Long( CustomerId ) );

        // Merge the data-model and the template
        //
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

