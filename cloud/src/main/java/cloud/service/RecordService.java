package cloud.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.model.entity.Record;
import cloud.repository.RecordRepositoriy;

@Component
@Transactional
public class RecordService implements IRecordService{
	@Autowired
	Environment env;
	
	@Resource
	RecordRepositoriy repo;
	
	@Override
	public List<Record> getRecords() {
		return repo.getRecords();
	}
	
	@Override
	public Record getRecord(int id) {
		Record r = repo.getRecord(id);
//		r.setImagePath(r.getImagePathLob().toString());
		if (r != null)
			return r;
		else
			throw new RuntimeException("The record does not exists.");
	}
	
	@Override
	public Record createRecord(Record r) {
//		r.setImagePathLob(r.getImagePath().getBytes());
		r.setCreatedDate(new Date());
		r.setLastUpdateDate(new Date());
		r.setDeleted(false);
		r.setDone(false);
		Record newRecord = repo.save(r);
		return newRecord;
	}
	
	@Override
	public void updateRecord(Record newRecord) {
		Record temp = getRecord(newRecord.getId());
		if (temp == null)
			throw new RuntimeException(env.getProperty("object_not_found"));
		
		temp.setLastUpdateDate(new Date());
		temp.setDescription(newRecord.getDescription());
		temp.setGpsLatitude(newRecord.getGpsLatitude());
		temp.setGpsLongitude(newRecord.getGpsLongitude());
		temp.setImagePath(newRecord.getImagePath());
		temp.setDone(newRecord.getDone());
		temp.setDeleted(newRecord.isDeleted());
		repo.save(temp);
	}
	
	@Override
	public void deleteRecord(int id) {
		Record temp = getRecord(id);
		if (temp == null)
			throw new RuntimeException(env.getProperty("object_not_found"));
		temp.setDeleted(true);
		temp.setLastUpdateDate(new Date());
		repo.save(temp);
	}
}
