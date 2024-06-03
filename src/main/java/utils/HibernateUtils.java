package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * The hibernate utils class
 * 
 * @author Samuel Calderón González
 *
 */
public class HibernateUtils {
	private static SessionFactory sesionFactory;
	private static Session session;

	/**
	 * Sets the session factory
	 */
	private static void setSessionFactory() {
		if (sesionFactory == null) {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure().build();
			sesionFactory = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
		}
	}

	/*
	 * Gets the session factory
	 */
	public SessionFactory getSessionFactory() {
		setSessionFactory();
		return sesionFactory;
	}

	/**
	 * Get the current session
	 * 
	 * @return session
	 */
	public static Session getSession() {
		if (session == null) {
			if (sesionFactory == null)
				setSessionFactory();
			session = sesionFactory.openSession();
		}

		return session;
	}

	/**
	 * Close the session
	 */
	public static void close() {
		closeSession();
		closeSessionFactory();
	}

	/**
	 * Close the session
	 */
	public static void closeSession() {
		if (session != null)
			session.close();
	}

	/**
	 * Close the session factory
	 */
	public static void closeSessionFactory() {
		if ((sesionFactory != null) && !sesionFactory.isClosed())
			sesionFactory.close();
	}

}
