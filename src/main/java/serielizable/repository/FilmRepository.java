package serielizable.repository;

import java.util.List;

import serielizable.entity.Film;

public class FilmRepository extends AbstractRepository{
	
	@SuppressWarnings("unchecked")
	public List<Film> getAllByUserId(int userId) {
		return session.createQuery("from Film where user_id = '" + userId + "'").list();
	}
	
	public void insertFilm(Film film) {
		try {
			beginTransaction();
			session.save(film);
			commitTransaction();
			System.out.println("Film registered succesfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	public void filter(int userId, String ) {
//		
//	}

	public void deleteFilm(Film film) {
		try {
			beginTransaction();
			session.remove(film);
			commitTransaction();
			System.out.println("Film deleted succesfully");
		} catch (Exception e) {
		}		
	}
	
	public void updateFilm(Film film) {
		try {
			beginTransaction();
			session.save(film);
			commitTransaction();
			System.out.println("Film updated succesfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
