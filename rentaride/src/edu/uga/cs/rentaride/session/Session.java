package edu.uga.cs.rentaride.session;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.logic.impl.LogicLayerImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;


public class Session 
    extends Thread
{
    private Connection conn;
    private ObjectLayer objectLayer;
    private LogicLayer logicLayer;
    private User user;
    private String id;
    private Date expiration;
    private static Logger log = SessionManager.getLog();
    
    public Session(Connection conn)
    {
        this.conn = conn;
        objectLayer = new ObjectLayerImpl();
        PersistenceLayer persistence = new PersistenceLayerImpl(conn, objectLayer);
        objectLayer.setPersistence(persistence);
        logicLayer = new LogicLayerImpl(objectLayer);
        extendExpiration();
    }
    
    /***********************************************************
     * Gets the JDBC connection information for the current session.
     * @return current connection
     */
    public Connection getConnection()
    {
        extendExpiration();
        return conn;
    }
    
    public User getUser()
    {
        extendExpiration();
        return user;
    }
    
    public void setUser(User user) 
            throws RARException
    {
        extendExpiration();
        this.user = user;
    }
    
    public String getSessionId()
    {
        extendExpiration();
        return id;
    }
    
    public void setSessionId(String id)
    {
        this.id = id;
    }
    
    public Date getExpiration() 
    { 
        return expiration;
    }
    
    public void setExpiration(Date expiration)
    {
        this.expiration = expiration;
    }
    
    private void extendExpiration(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 30);
        this.expiration = c.getTime();
    }
    
    public ObjectLayer getObjectLayer()
    {
        return objectLayer;
    }

    public void run()
    {
        long diff = expiration.getTime() - System.currentTimeMillis();
        while (diff > 0) {
            try {
                sleep(diff);
            } 
            catch(Exception e) {
                break;
            }
            diff = expiration.getTime() - System.currentTimeMillis();
        }
        try {
            SessionManager.removeSession(this);
        } 
        catch(RARException e) {
            log.error(e.toString(), e);
            try {
                throw e;
            } 
            catch (RARException e1) {
                e1.printStackTrace();
            }
        }
    }

    public LogicLayer getLogicLayer()
    {
        return logicLayer;
    }

    public void setLogicLayer(LogicLayer logicLayer)
    {
        this.logicLayer = logicLayer;
    }
    
}

