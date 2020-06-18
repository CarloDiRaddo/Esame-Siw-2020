package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.esame.model.Credentials;
import it.uniroma3.siw.esame.repository.CredentialsRepository;

@Service
public class CredentialsService {
    @Autowired
    protected CredentialsRepository credentialsRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    
    @Transactional
    public Credentials getCredentials(long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

   
    @Transactional
    public Credentials getCredentials(String userName) {
        Optional<Credentials> result = this.credentialsRepository.findByUserName(userName);
        return result.orElse(null);
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
    
    @Transactional
    public Credentials updateCredentialsPassword(Credentials credentials) {
    	credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword())); 
    	 return this.credentialsRepository.save(credentials);
    	 }

    
    @Transactional
    public List<Credentials> getAllCredentials() {
        List<Credentials> result = new ArrayList<>();
        Iterable<Credentials> iterable = this.credentialsRepository.findAll();
        for(Credentials credentials : iterable)
            result.add(credentials);
        return result;
    }
}
