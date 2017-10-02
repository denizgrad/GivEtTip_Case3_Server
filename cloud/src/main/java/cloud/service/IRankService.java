package cloud.service;

import java.util.List;
import cloud.model.entity.Rank;

public interface IRankService {
	
	public List<Rank> getRanking();
	public List<Rank> getRanking(int offset, int limit);

}
