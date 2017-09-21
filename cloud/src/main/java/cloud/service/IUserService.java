package cloud.service;

import java.util.List;

import cloud.model.entity.User;

public interface IUserService {
	public List<User> getUsers();
	public User getUser(int id);
	public User getUserByEmail(String email);
	public boolean doesUserExists(String email);
	public void createUser(User u) throws Exception;
	public void updateUser(User newUser) throws Exception;
	public void deleteUser(int id);
	public int login(User u) throws Exception;
}
