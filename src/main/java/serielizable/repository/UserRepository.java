package serielizable.repository;

import serielizable.entity.User;

public class UserRepository extends AbstractRepository {

	public User getByUserName(String userName) {
		try {
			return (User) session.createQuery("from User where user_name = '" + userName + "'").list().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public void insertUser(User user) {
		try {
			beginTransaction();
			session.save(user);
			commitTransaction();
			System.out.println("User registered succesfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean exists(String userName) {
		try {
			session.createQuery("from User where user_name = '" + userName + "'").list().get(0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
