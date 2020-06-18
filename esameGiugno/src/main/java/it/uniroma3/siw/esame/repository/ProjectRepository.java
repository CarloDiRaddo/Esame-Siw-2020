package it.uniroma3.siw.esame.repository;

import org.springframework.stereotype.Repository;

import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	List<Project> findByProprietario(User proprietario);
	List<Project> findByUtentiConVisibilità(User user);
	
}
