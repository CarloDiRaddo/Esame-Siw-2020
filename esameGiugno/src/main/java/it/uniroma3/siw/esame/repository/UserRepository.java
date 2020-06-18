package it.uniroma3.siw.esame.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public List<User> findByProgettiVisibili(Project project);
	
}
