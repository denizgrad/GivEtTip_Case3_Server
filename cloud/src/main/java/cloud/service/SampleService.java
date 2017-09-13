package cloud.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cloud.model.entity.Sample;
import cloud.repository.SampleRespository;
@Component
@Transactional
public class SampleService implements ISampleService{
	@Resource
	SampleRespository repo;
	
	@Override
	public void create(Sample sample) {
		repo.save(sample);
	}
	@Override
	public List<Sample> listAll() {
		return repo.listAll();
	}

}
