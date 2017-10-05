package cloud.service;
import java.util.List;

import cloud.model.entity.Record;
import cloud.model.entity.RecordCoordinate;

public interface IRecordCoordinateService {
	
	public List<RecordCoordinate> getRecordsCoordinates();

}
