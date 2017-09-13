package cloud.service;

import java.util.List;

import cloud.model.entity.Sample;

public interface ISampleService {
	public void create(Sample sample);
	public List<Sample> listAll();
}
