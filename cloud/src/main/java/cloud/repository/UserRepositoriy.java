package cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cloud.model.entity.User;

public interface UserRepositoriy extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User u")
	public List<User> getUsers();
	
	@Query("SELECT u FROM User u WHERE u.id = :id")
	public User getUser(@Param("id") int id);
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);

}
