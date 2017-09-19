package cloud.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

//import java.math.BigDecimal;

@Entity
public class Record extends BaseModel {
//	@Id
//	@SequenceGenerator(name = "id", sequenceName = "id")
//	@GeneratedValue(strategy = GenerationType.AUTO, generator="id")  
//	private int id;
	
	@ManyToOne
	private User author;
	
	@Column(nullable = false)
	private String imagePath;
	
	@Column(nullable = false)
	private double gpsLatitude;
	
	@Column(nullable = false)
	private double gpsLongitude;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private boolean done;
	
	/* public methods */
	
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public double getGpsLatitude() {
		return gpsLatitude;
	}
	public void setGpsLatitude(double gpsLatitude) {
//		gpsLatitude.setScale(120, BigDecimal.ROUND_HALF_UP);
		this.gpsLatitude = gpsLatitude;
	}	
	public double getGpsLongitude() {
		return gpsLongitude;
	}
	public void setGpsLongitude(double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}
