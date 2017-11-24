package edu.uga.cs.rentaride.session;


import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.persistence.impl.DbUtils;




public class SessionManager
{
    private static Map<String, Session> sessions;
    
    private static Map<String, Session> loggedIn;
    
    private static Logger log = Logger.getLogger(SessionManager.class);
    
    static{
        sessions = new HashMap<String, Session>();
        loggedIn = new HashMap<String, Session>();
    } 
    
    public static Session createSession() 
            throws RARException 
    {
        Connection conn = null;
        Session s = null;
        
        try {
            conn = DbUtils.connect();
        } catch (Exception seq) {
            throw new RARException("SessionManager.login: Unable to get a database connection");
        }
        
        s = new Session(conn);
        
        return s; 
    }
    
    public static String storeSession(Session session)
            throws RARException
    {
        User user = session.getUser();
        
        if(loggedIn.containsKey(user.getUserName())) {
            Session qs = loggedIn.get(user.getUserName());
            qs.setUser(user);
            return qs.getSessionId();
        }
                
        String ssid = secureHash("RENTARIDE" + System.nanoTime());
        session.setSessionId(ssid);
        
        sessions.put(ssid, session);
        loggedIn.put(user.getUserName(), session);
        session.start();
        return ssid;
    }
    
    public static void logout(Session s) 
            throws RARException
    {
        s.setExpiration(new Date());
        s.interrupt();
        removeSession(s);
    }
    
    public static void logout(String ssid) 
            throws RARException
    {
        Session s = getSessionById(ssid);
        s.setExpiration(new Date());
        s.interrupt();
        removeSession(s);
    }
    
    protected static void removeSession(Session s)
            throws RARException
    {
        try { 
            s.getConnection().close();
        } 
        catch(SQLException sqe) {
            log.error("SessionManager.removeSession: cannot close connection", sqe);
            throw new RARException("SessionManager.removeSession: Cannot close connection");
        } // try
        sessions.remove(s.getSessionId());
        loggedIn.remove(s.getUser().getUserName());
    }
    
    public static Session getSessionById(String ssid){
        return sessions.get(ssid);
    }
    
    public static String secureHash(String input)
            throws RARException
    {
        StringBuilder output = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            for(int i = 0; i < digest.length; i++) {
                String hex = Integer.toHexString(digest[i]);
                if(hex.length() == 1)
                    hex = "0" + hex;
                hex = hex.substring(hex.length() - 2);
                output.append(hex);
            }
        }
        catch(Exception e) {
            log.error(
                    "SessionManager.secureHash: Invalid Encryption Algorithm", e);
            throw new RARException(
                    "SessionManager.secureHash: Invalid Encryption Algorithm");
        }
        return output.toString();
    }
    
    public static Logger getLog()
    {
        return log; 
    }

}

