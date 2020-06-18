package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.repository.UserRepository;

@Service
public class UserService {
	 	
		@Autowired
	    protected UserRepository userRepository;

	    @Transactional
	    public User getUser(long id) {
	        Optional<User> result = this.userRepository.findById(id);
	        return result.orElse(null);
	    }
	    
	    @Transactional
	    public User saveUser(User user) {
	        return this.userRepository.save(user);
	    }

	   /**Ottieni tutti gli utenti dal db*/
	    @Transactional
	    public List<User> getAllUsers() {
	        List<User> result = new ArrayList<>();
	        Iterable<User> iterable = this.userRepository.findAll(); 
	        for(User user : iterable)
	            result.add(user);
	        return result;
	    }

	    /**Ottieni tutti gli utenti che hanno
	    *visibilita sul progetto p */
	    
		public List<User> getUtentiConVisibilità(Project project) {
		
			return this.userRepository.findByProgettiVisibili(project);
		}

}
