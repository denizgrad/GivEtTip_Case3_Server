package cloud.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.model.entity.Rank;
import cloud.model.entity.User;

@Component
@Transactional
public class RankService implements IRankService {
	
	@Autowired
	IUserService usr;
	
	public List<Rank> getRanking() {
		List<User> users = usr.getUsers();
		Collections.sort(users, Collections.reverseOrder());
		
		List<Rank> res = new ArrayList<Rank>();
		int i = 1;
		for (User u : users) {
			Rank r = new Rank(u.getId(), i, u.getScore(), u.getEmail());
			res.add(r);
			i++;
		}
		return res;
	}
	
	public List<Rank> getRanking(int offset, int limit) {
		return null;
	}
}
