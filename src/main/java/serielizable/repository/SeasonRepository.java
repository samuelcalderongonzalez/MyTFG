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

}
