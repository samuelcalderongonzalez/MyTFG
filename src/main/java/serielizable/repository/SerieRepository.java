package serielizable.repository;

import java.util.List;

import serielizable.entity.Serie;

/**
 * The serie repository class
 * 
 * @author Samuel Calderón González
 *
 */
public class SerieRepository extends AbstractRepository {
	/**
	 * Gets all the series by an user id
	 * 
	 * @param userId
	 * @return a list of series
	 */
	@SuppressWarnings("unchecked")
	public List<Serie> getAllByUserId(int userId) {
		return session.createQuery("from Serie where user_id = '" + userId + "'").list();
	}

	/**
	 * Inserts a serie
	 * 
	 * @param serie
	 */
	public void insertSerie(Serie serie) {
		try {
			beginTransaction();
			session.save(serie);
			commitTransaction();
			System.out.println("Serie registered succesfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete a serie
	 * 
	 * @param serie
	 */
	public void deleteSerie(Serie serie) {
		try {
			beginTransaction();
			session.remove(serie);
			commitTransaction();
			System.out.println("Serie deleted succesfully");
		} catch (Exception e) {
		}
	}

	/**
	 * Update a serie
	 * 
	 * @param serie
	 */
	public void updateSerie(Serie serie) {
		try {
			beginTransaction();
			session.save(serie);
			commitTransaction();
			System.out.println("Serie updated succesfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
