package cloud.service;

import java.util.List;
import java.util.Date;

import javax.annotation.Resource;

//import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.model.entity.Record;
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
	public User getUser(int id) {
		return repo.getUser(id);
	}
	
	@Override
	public void createUser(User u) {
		repo.save(u);
	}
	
	@Override
	public void updateUser(User newUser) {
		User temp = getUser(newUser.getId());
		temp.setLastUpdateDate(new Date());
		temp.setEmail(newUser.getEmail());
		temp.setName(newUser.getName());
		temp.setSurname(newUser.getSurname());
		temp.setActive(newUser.getActive());
		temp.setDeleted(newUser.isDeleted());
		if (StringUtils.isNotEmpty(newUser.getPassword()) && StringUtils.isNotBlank(newUser.getPassword()))
			temp.setPassword(newUser.getPassword());
		 repo.save(temp);
	}
	
	@Override
	public void deleteUser(int id) {
		User temp = getUser(id);
		temp.setDeleted(true);
		repo.save(temp);
	}
}
