package it.uniroma3.siw.esame.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.esame.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>{
	
	List<Tag> findByNome(String nome);

	
	
	
	

}
