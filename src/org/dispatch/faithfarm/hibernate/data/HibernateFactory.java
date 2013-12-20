package org.dispatch.faithfarm.hibernate.data;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionImplementor;

	public class HibernateFactory {
	   
	    private static Log log = LogFactory.getLog(HibernateFactory.class);
	    
	    private static final SessionFactory sessionFactory;
	    
	    static {
	        try {
	            // Create the SessionFactory
	        	 sessionFactory = new Configuration().configure().buildSessionFactory();
	            
	        } catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            log.error("Initial SessionFactory creation failed.", ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
	    
	    public static final ThreadLocal session = new ThreadLocal();
	    
	    public static Session openSession() throws HibernateException {
	       Session s = sessionFactory.openSession();
	       session.set(s);
	       return s;
	    }
	 
	    
	    
	 
	}

	 
	