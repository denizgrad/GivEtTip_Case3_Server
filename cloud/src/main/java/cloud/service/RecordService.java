package cloud.service;

import java.util.List;

import javax.annotation.Resource;

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
		
		// TODO: Copy all properties. Is there an easy way?
		temp.setAuthor(newRecord.getAuthor());
		// ...
		// repo.save(temp);
	}
}
