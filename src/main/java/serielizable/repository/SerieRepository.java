package serielizable.repository;

import java.util.List;

import serielizable.entity.Serie;

public class SerieRepository extends AbstractRepository{
	
	@SuppressWarnings("unchecked")
	public List<Serie> getAllByUserId(int userId) {
		return session.createQuery("from Serie where user_id = '" + userId + "'").list();
	}
}
