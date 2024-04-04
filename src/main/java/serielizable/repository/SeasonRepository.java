package serielizable.repository;

import java.util.List;

import serielizable.entity.Season;

public class SeasonRepository extends AbstractRepository {

	@SuppressWarnings("unchecked")
	public List<Season> getAllByUserIdAndSerieId(int userId, int serieId) {
		try {
			return session.createQuery("from Season where user_id = '" + userId + "' AND serie_id '" + serieId + "'")
					.list();
		} catch (Exception e) {
			return null;
		}
	}
	
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
