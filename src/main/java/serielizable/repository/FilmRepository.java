package serielizable.repository;

import java.util.List;

import serielizable.entity.Film;

public class FilmRepository extends AbstractRepository{
	
	@SuppressWarnings("unchecked")
	public List<Film> getAllByUserId(int userId) {
		return session.createQuery("from Film where user_id = '" + userId + "'").list();
	}
}
