
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


public class CreateVehicle
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
        
		String make;
	    String model;
	    int year;
	    String tag;
	    int mileage;
	    String lastServiced;
	    String vehicleType;
	    long rentalLocationId;
	    String vehicleCondition;
	    String vehicleStatus;
		long vehicleId = 0;
		
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
		 make = req.getParameter("make");
		 model = req.getParameter("model");
		 String hmm = req.getParameter("year");
     year = Integer.parseInt(hmm);
		 tag = req.getParameter("tag");
		 String aight = req.getParameter("mileage");
      mileage = Integer.parseInt(aight);
		 lastServiced = req.getParameter("lastServiced");
		 vehicleType = req.getParameter("vehicleType");
		 String another = req.getParameter("rentalLocationId");
     rentalLocationId = Long.parseLong(another);
		 vehicleCondition = req.getParameter("vehicleCondition");
		 vehicleStatus = req.getParameter("vehicleStatus");
		 
        if( make == null) {
            RARError.error( cfg, toClient, "Unspecified make" );
            return;
        }
		if( model == null ) {
            RARError.error( cfg, toClient, "Unspecified model" );
            return;
        }
		if( year == 0 ) {
            RARError.error( cfg, toClient, "Year = 0" );
            return;
        }
		if( tag == null ) {
            RARError.error( cfg, toClient, "Unspecified tag" );
            return;
        }
		if( mileage <= 0 ) {
            RARError.error( cfg, toClient, "Mileage is less than 0" );
            return;
        }
		if( lastServiced == null ) {
            RARError.error( cfg, toClient, "Unspecified last serviced state" );
            return;
        }
		if( vehicleType == null ) {
            RARError.error( cfg, toClient, "Unspecified vehicle type" );
            return;
        }
		if( rentalLocationId <= 0 ) {
            RARError.error( cfg, toClient, "Rental Location ID is invalid" );
            return;
        }
		if( vehicleCondition == null ) {
            RARError.error( cfg, toClient, "Unspecified vehicle condition" );
            return;
        }
		if( vehicleStatus == null ) {
            RARError.error( cfg, toClient, "Unspecified vehicle status" );
            return;
        }
		
		

		/*
		if( id <= 0 ) {
            RARError.error( cfg, toClient, "ID is a negative number " );
            return;
        }
		*/
/*
        try {
            founder_id = Long.parseLong( person_id_str );
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "founder_id should be a number and is: " + person_id_str );
            return;
        }
		*/

        /*try {
            vehicleId = logicLayer.createVehicle( make,
	     model,
	     year,
	     tag,
	     mileage,
	     lastServiced,
	     vehicleType,
	     rentalLocationId,
	     vehicleCondition,
	     vehicleStatus);
        } 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        }*/

        // Setup the data-model
        //
        Map<String,Object> root = new HashMap<String,Object>();

        // Build the data-model
        //
        root.put( "make", make );
		root.put( "model", model );
		root.put( "tag", tag );
		root.put( "vehicleType", vehicleType );
        root.put( "vehicleId", new Long( vehicleId ) );

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

