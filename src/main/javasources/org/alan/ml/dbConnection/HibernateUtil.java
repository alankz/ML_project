package org.alan.ml.dbConnection;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	final static Logger logger = LogManager.getLogger(HibernateUtil.class);

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
        	logger.info("Start buildSessionFactory");
        	Configuration conf = new Configuration();
            return conf.configure().buildSessionFactory();
        }
        catch (Throwable ex) {
        	logger.error("Failed to initial SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session openSession() {
   	  return sessionFactory.openSession();
    }

    public static void shutdown() {
    	getSessionFactory().close();
    }

}