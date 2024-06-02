package serielizable.repository;

import java.util.List;

import serielizable.entity.Film;

/**
 * The film repository class
 * 
 * @author Samuel Calderón González
 *
 */
public class FilmRepository extends AbstractRepository {
	/**
	 * Gets all the films by an user id
	 * 
	 * @param userId
	 * @return a list of films
	 */
	@SuppressWarnings("unchecked")
	public List<Film> getAllByUserId(int userId) {
		return session.createQuery("from Film where user_id = '" + userId + "'").list();
	}

	/**
	 * Inserts a new film
	 * 
	 * @param film
	 */
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

	/**
	 * Delete a film
	 * 
	 * @param film
	 */
	public void deleteFilm(Film film) {
		try {
			beginTransaction();
			session.remove(film);
			commitTransaction();
			System.out.println("Film deleted succesfully");
		} catch (Exception e) {
		}
	}

	/**
	 * Updates a film
	 * 
	 * @param film
	 */
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
