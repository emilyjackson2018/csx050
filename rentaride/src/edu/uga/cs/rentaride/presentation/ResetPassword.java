import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/ResetPassword")
public class ResetPassword extends HttpServlet{
	
private static final long serialVersionUID = 1L;
    
    static  String  templateDir = "WEB-INF/templates";
    //static  
    String  resultTemplateName = "ResetPassword-result.ftl";

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
        String		   resetPassword = null; 
        LogicLayer	   logicLayer = null;
        BufferedWriter toClient = null;
        String         ssid = null;

        
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
        resetPassword = req.getParameter("password");
        session.getUser().setPassword(resetPassword);
        System.out.println("ResetPassword servlet new password: " + session.getUser().getPassword());

        //if (session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.CustomerImpl") 	  ; //
    	//
        
        if( resetPassword == null ) {
            RARError.error( cfg, toClient, "Unspecified password" );
            return;
        }
        
        if (session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.AdministratorImpl") {
        	Administrator admin = (Administrator) session.getUser();
        	try {
        		logicLayer.resetPassword(admin);
        	} catch (RARException e) {
        		e.printStackTrace();
        	}
        }
        if (session.getUser().getClass().getName() == "edu.uga.cs.rentaride.entity.impl.CustomerImpl") {
        	Customer customer = (Customer) session.getUser();
        	try {
        		logicLayer.resetPassword(customer);
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
