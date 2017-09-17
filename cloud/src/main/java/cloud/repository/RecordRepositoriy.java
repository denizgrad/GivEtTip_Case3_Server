package cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cloud.model.entity.Record;

public interface RecordRepositoriy extends JpaRepository<Record, Long>{
	
	@Query("SELECT r FROM Record r")
	public List<Record> getRecords();
	
	@Query("SELECT r FROM Record r WHERE r.id = :id")
	public Record getRecord(@Param("id") int id);
	
	/*
	public List<Record> getRecords();
	public Record getRecord(int id);
	public void createRecord(Record r);
	public void updateRecord(Record newRecord);
	*/

}
