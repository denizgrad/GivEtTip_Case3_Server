package cloud.service;

import java.util.List;

import cloud.model.entity.User;

public interface IUserService {
	public List<User> getUsers();
	public User getUser(String userId);
	public void createUser(User u);
	public void updateUser(User newUser);
}
