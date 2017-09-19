package cloud.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.model.entity.Record;
import cloud.repository.RecordRepositoriy;

@Component
@Transactional
public class RecordService implements IRecordService{
	@Resource
	RecordRepositoriy repo;
	
	@Override
	public List<Record> getRecords() {
		return repo.getRecords();
	}
	
	@Override
	public Record getRecord(int id) {
		return repo.getRecord(id);
	}
	
	@Override
	public void createRecord(Record r) {
		repo.save(r);
	}
	
	@Override
	public void updateRecord(Record newRecord) {
		Record temp = getRecord(newRecord.getId());
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
		temp.setDeleted(true);
		repo.save(temp);
	}
}
