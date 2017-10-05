package cloud.model.entity;

import java.math.BigDecimal;

public class Rank {
	
	private int id;
	private int userId;
	private int position;
	private BigDecimal score;
	private String userEmail;
	// private String scoreString;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	//public String getScoreString() {
	//	return this.getPosition() + ": " + this.getUserEmail() + " - " + this.getScore();
	//}
	
	public Rank(int userId, int position, BigDecimal score, String userEmail) {
		this.userId = userId;
		this.position = position;
		this.score = score;
		this.userEmail = userEmail;
	}

}
