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
}
