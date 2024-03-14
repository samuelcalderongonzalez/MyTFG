package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
	private static SessionFactory sesionFactory;
	private static Session session;

	private static void setSessionFactory() {
		if (sesionFactory == null) {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure().build();
			sesionFactory = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
		}
	}

	public SessionFactory getSessionFactory() {
		setSessionFactory();
		return sesionFactory;
	}

	public static Session getSession() {
		if (session == null) {
			if (sesionFactory == null)
				setSessionFactory();
			session = sesionFactory.openSession();
		}

		return session;
	}

	public static void close() {
		closeSession();
		closeSessionFactory();
	}

	public static void closeSession() {
		if (session != null)
			session.close();
	}

	public static void closeSessionFactory() {
		if ((sesionFactory != null) && !sesionFactory.isClosed())
			sesionFactory.close();
	}

}
