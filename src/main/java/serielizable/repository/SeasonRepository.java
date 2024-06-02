package serielizable.repository;

import java.util.List;

import serielizable.entity.Season;

/**
 * The season repository class
 * 
 * @author Samuel Calderón González
 *
 */
public class SeasonRepository extends AbstractRepository {

	/**
	 * Gets all the seasons by a serie id and user id
	 * 
	 * @param userId
	 * @param serieId
	 * @return a list of season
	 */
	@SuppressWarnings("unchecked")
	public List<Season> getAllByUserIdAndSerieId(int userId, int serieId) {
		try {
			return session.createQuery("from Season where user_id = '" + userId + "' AND serie_id '" + serieId + "'")
					.list();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Updates a season
	 * 
	 * @param season
	 */
	public void updateSeason(Season season) {
		try {
			beginTransaction();
			session.save(season);
			commitTransaction();
			System.out.println("Season updated succesfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
