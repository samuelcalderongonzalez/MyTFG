package serielizable.repository;

import java.util.List;

import serielizable.entity.Serie;

public class SerieRepository extends AbstractRepository{
	
	@SuppressWarnings("unchecked")
	public List<Serie> getAllByUserId(int userId) {
		return session.createQuery("from Serie where user_id = '" + userId + "'").list();
	}
	
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
	
	
	public boolean completedDateExists(Serie serie) {
		try {
			Serie s = (Serie) session.createQuery("from Serie where id = " + serie.getId() + " and user_id = " + serie.getUserId()).list().get(0);
			if(s.getCompletedDate() != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public boolean reviewExists(Serie serie) {
		try {
			Serie s = (Serie) session.createQuery("from Serie where id = " + serie.getId() + " and user_id = " + serie.getUserId()).list().get(0);
			if(s.getReview() != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
}
