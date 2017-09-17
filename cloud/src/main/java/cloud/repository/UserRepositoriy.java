package cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cloud.model.entity.User;

public interface UserRepositoriy extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User u")
	public List<User> getUsers();
	
	@Query("SELECT u FROM User u WHERE u.userId = :userId")
	public User getUser(@Param("userId") String userId);
	
	/*
	public List<User> getUsers();
	public User getUser(String userId);
	public void createUser(User u);
	public void updateUser(User newUser);
	*/

}
