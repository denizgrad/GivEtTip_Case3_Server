package cloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.model.entity.RecordCoordinate;
import cloud.model.entity.Record;

@Component
@Transactional
public class RecordCoordinateService implements IRecordCoordinateService {
	
	@Autowired
	IRecordService irec;
	
	public List<RecordCoordinate> getRecordsCoordinates() {
		List<Record> records = irec.getRecords();
		List<RecordCoordinate> res = new ArrayList<RecordCoordinate>();
		for (Record r : records) {
			res.add(new RecordCoordinate(r));
		}
		return res;
	}

}
