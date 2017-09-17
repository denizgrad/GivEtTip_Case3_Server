package cloud.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.model.entity.User;
import cloud.repository.UserRepositoriy;

@Component
@Transactional
public class UserService implements IUserService {
	@Resource
	UserRepositoriy repo;
	
	@Override
	public List<User> getUsers() {
		return repo.getUsers();
	}
	
	@Override
	public User getUser(String userId) {
		return repo.getUser(userId);
	}
	
	@Override
	public void createUser(User u) {
		repo.save(u);
	}
	
	@Override
	public void updateUser(User newUser) {
		User temp = getUser(newUser.getUserId());
		
		// TODO: Copy all properties. Is there an easy way?
		temp.setActive(newUser.getActive());
		// ...
		// repo.save(temp);
	}

}
