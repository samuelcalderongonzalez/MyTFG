package serielizable.repository;

import serielizable.entity.User;

/**
 * The user repository class
 * 
 * @author usuario
 *
 */
public class UserRepository extends AbstractRepository {
	/**
	 * Gets a user by the name
	 * 
	 * @param userName
	 * @return the user if exist, null if not
	 */
	public User getByUserName(String userName) {
		try {
			return (User) session.createQuery("from User where user_name = '" + userName + "'").list().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Insert a user
	 * 
	 * @param user
	 */
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

	/**
	 * Return if the provided users either exists or not
	 * 
	 * @param userName
	 * @return true or false
	 */
	public boolean exists(String userName) {
		try {
			session.createQuery("from User where user_name = '" + userName + "'").list().get(0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
