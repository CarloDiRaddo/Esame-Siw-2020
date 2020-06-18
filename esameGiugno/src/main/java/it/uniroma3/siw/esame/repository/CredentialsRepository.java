package it.uniroma3.siw.esame.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.esame.model.Credentials;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	Optional<Credentials> findByUserName(String username);

}
