package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FindAllCustomers
    extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    private static  String         templateDir = "WEB-INF/templates";
    private static  String         resultTemplateName = "FindAllCustomers-Result.ftl";

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

    public void doGet( HttpServletRequest  req, HttpServletResponse res )
            throws ServletException, IOException
    {
        Template            resultTemplate = null;
        BufferedWriter      toClient = null;
        LogicLayer          logicLayer = null;
        List<Reservation>        rv = null;
        List<List<Object>>  reservations = null;
        List<Object>        reservation = null;
        Reservation              r  = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;


        // Load templates from the WEB-INF/templates directory of the Web app.
        //
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        } 
        catch( IOException e ) {
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
        if( httpSession == null ) {       // not logged in!
            ClubsError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }

        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid == null ) {       // not logged in!
            ClubsError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }

        session = SessionManager.getSessionById( ssid );
        if( session == null ) {
            ClubsError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return; 
        }

        logicLayer = session.getLogicLayer();
        if( logicLayer == null ) {
            ClubsError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return; 
        }

        // Get the servlet parameters
        //
        // No parameters here

        // Setup the data-model
        //
        Map<String,Object> root = new HashMap<String,Object>();

        try {
            rv = logicLayer.findAllCustomers();

            // Build the data-model
            //
            reservations = new LinkedList<List<Object>>();
            root.put( "reservations", reservations );
            for( int i = 0; i < rv.size(); i++ ) {
                r = (Reservation) rv.get( i );
                reservation = new LinkedList<Object>();
                reservation.add( r.getPickupTime() );
                reservation.add( r.getLength() );
                reservation.add( r.getVehicleType() );
                reservation.add( r.getRentalLocation) );
                reservation.add( r.getCustomer() );		
                reservations.add( reservation );
            }
        } 
        catch( Exception e) {
            ClubsError.error( cfg, toClient, e );
            return;
        }

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

