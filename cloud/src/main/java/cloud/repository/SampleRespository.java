package cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cloud.model.entity.Sample;

public interface SampleRespository extends JpaRepository<Sample, Long>{
	
	@Query("SELECT s FROM Sample s WHERE s.name = :name")
	public Sample getByName(@Param("name") String name);
	@Query("SELECT s FROM Sample s")
	public List<Sample> listAll();
}

