package serielizable.repository;

import java.util.List;

import org.hibernate.Session;

import utils.HibernateUtils;

public abstract class AbstractRepository {
	protected Session session;

	protected AbstractRepository() {
		this.session = HibernateUtils.getSession();
	}

	protected void beginTransaction() {
		session.beginTransaction();
	};

	protected void commitTransaction() {
		session.getTransaction().commit();
	};

	@SuppressWarnings("rawtypes")
	protected List getAll(String clase) {
		return session.createQuery("from " + clase).list();
	}

}
