package cloud.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
	private String gpsLatitude;
	
	@Column(nullable = false)
	private String gpsLongitude;
	
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
	public String getGpsLatitude() {
		return gpsLatitude;
	}
	public void setGpsLatitude(String gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}
	public String getGpsLongitude() {
		return gpsLongitude;
	}
	public void setGpsLongitude(String gpsLongitude) {
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
