package cloud.service;

import java.util.List;
import java.util.Date;

import javax.annotation.Resource;

//import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.controller.EncryptionUtility;
import cloud.model.entity.User;
import cloud.repository.UserRepositoriy;

@Component
@Transactional
public class UserService implements IUserService {
	@Autowired
	Environment env;

	@Resource
	UserRepositoriy repo;

	@Override
	public List<User> getUsers() {
		return repo.getUsers();
	}

	@Override
	public User getUser(int id) {
		User u = repo.getUser(id);
		if (u != null)
			return u;
		else
			throw new RuntimeException("The user does not exists.");
	}

	@Override
	public User getUserByEmail(String email) {
		return repo.getUserByEmail(email);
	}

	@Override
	public boolean doesUserExists(String email) {
		if (getUserByEmail(email) != null)
			return true;
		else
			return false;
	}

	@Override
	public void createUser(User u) throws Exception {
		u.setPassword(EncryptionUtility.getSaltedHash(u.getPassword()));
		u.setCreatedDate(new Date());
		u.setLastUpdateDate(new Date());
		u.setActive(true);
		u.setDeleted(false);
		repo.save(u);
	}

	@Override
	public void updateUser(User newUser) throws Exception {
		User temp = getUser(newUser.getId());
		if (temp == null)
			throw new RuntimeException(env.getProperty("object_not_found"));

		temp.setLastUpdateDate(new Date());
		temp.setEmail(newUser.getEmail());
		temp.setName(newUser.getName());
		temp.setSurname(newUser.getSurname());
		temp.setActive(newUser.getActive());
		temp.setDeleted(newUser.isDeleted());
		if (StringUtils.isNotEmpty(newUser.getPassword()) && StringUtils.isNotBlank(newUser.getPassword()))
			temp.setPassword(EncryptionUtility.getSaltedHash(newUser.getPassword()));
		repo.save(temp);
	}

	@Override
	public void deleteUser(int id) {
		User temp = getUser(id);
		if (temp == null)
			throw new RuntimeException(env.getProperty("object_not_found"));
		temp.setDeleted(true);
		temp.setLastUpdateDate(new Date());
		repo.save(temp);
	}
}
