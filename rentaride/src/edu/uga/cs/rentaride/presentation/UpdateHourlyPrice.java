
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



import edu.uga.cs.rentaride.entity.User;

import edu.uga.cs.rentaride.logic.LogicLayer;

import edu.uga.cs.rentaride.session.Session;

import edu.uga.cs.rentaride.session.SessionManager;

import freemarker.template.Configuration;

import freemarker.template.Template;

import freemarker.template.TemplateException;



import edu.uga.cs.rentaride.RARException;



@WebServlet("UpdateHourlyPrice")



public class UpdateHourlyPrice

    extends HttpServlet 

{

    private static final long serialVersionUID = 1L;

    static  String         templateDir = "WEB-INF/templates";

    static  String         resultTemplateName = “UpdateHourlyPrice-Result.ftl";



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

        String		   vehicleType = null;

        String	       price1 = null;





        double		   price = 0.0;

        LogicLayer     logicLayer = null;

        HttpSession    httpSession;

        Session        session;

        String         ssid;

        Map<String,Object> root = new HashMap<String,Object>();

        String retMessage = "";



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






        // Session Tracking

        httpSession = req.getSession();

        ssid = (String) httpSession.getAttribute("ssid");

        if (ssid != null) {

            System.out.println("Already have ssid: " + ssid);

            session = SessionManager.getSessionById(ssid);

            System.out.println("Connection: " + session.getConnection());

        } else

            System.out.println("ssid is null");



        session = SessionManager.getSessionById(ssid);

        if(session == null){

            RARError.error( cfg, new BufferedWriter(new OutputStreamWriter(res.getOutputStream(), "UTF-8")),"Session expired or illegal; please log in" );

            return;

        }

        User user = session.getUser();

        root.put("username", user.getUserName());



        logicLayer = session.getLogicLayer();

        if( logicLayer == null ) {

        		RARError.error( cfg, toClient, "Session expired or illegal; please log in" );

            return;

        }



        // Get the form parameters

        //

        vehicleType = req.getParameter( "vehicleType" );

        price1 = req.getParameter( "hourlyPrice" );





        try{

            price = Double.valueOf(price1);



int price2 = (int) (price * 100);

            logicLayer.UpdateHourlyPrice(vehicleType, price2);

        }catch(Exception e) {

        	e.printStackTrace();

        }





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

