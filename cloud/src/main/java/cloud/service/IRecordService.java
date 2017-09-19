package cloud.service;

import java.util.List;

import cloud.model.entity.Record;

public interface IRecordService {
	public List<Record> getRecords();
	public Record getRecord(int id);
	public void createRecord(Record r);
	public void updateRecord(Record newRecord);
	public void deleteRecord(int id);
}
