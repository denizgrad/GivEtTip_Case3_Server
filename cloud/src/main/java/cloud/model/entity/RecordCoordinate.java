package cloud.model.entity;

public class RecordCoordinate {
	private int recordId;
	private int authorId;
	private String authorEmail;
	private double gpsLatitude;
	private double gpsLongitude;
	
	public RecordCoordinate(Record r) {
		this.recordId = r.getId();
		this.authorId = r.getAuthor().getId();
		this.authorEmail = r.getAuthor().getEmail();
		this.gpsLatitude = r.getGpsLatitude();
		this.gpsLongitude = r.getGpsLongitude();
	}
	
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getAuthorEmail() {
		return authorEmail;
	}
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}
	public double getGpsLatitude() {
		return gpsLatitude;
	}
	public void setGpsLatitude(double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}
	public double getGpsLongitude() {
		return gpsLongitude;
	}
	public void setGpsLongitude(double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}
}
