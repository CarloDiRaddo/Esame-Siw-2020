package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.esame.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
	
}
