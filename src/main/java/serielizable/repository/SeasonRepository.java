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
	
	
	public boolean completedDateExists(Season season) {
		try {
			Season s = (Season) session.createQuery("from Season where id = " + season.getId() + " and user_id = " + season.getUserId() + " and serie_id = " + season.getSerie().getId()).list().get(0);
			if(s.getCompletedDate() != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public boolean reviewExists(Season season) {
		try {
			Season s = (Season) session.createQuery("from Season where id = " + season.getId() + " and user_id = " + season.getUserId() + " and serie_id = " + season.getSerie().getId()).list().get(0);
			if(s.getReview() != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

}
