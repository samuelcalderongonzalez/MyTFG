package serielizable.repository;

import java.util.List;

import serielizable.entity.Footage;

public class FootageRepository extends AbstractRepository{
	
	@SuppressWarnings("unchecked")
	public List<Footage> getAllByUserId(int userId) {
		return session.createQuery("from Footage where user_id = '" + userId + "'").list();
	}
}
