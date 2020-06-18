package it.uniroma3.siw.esame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.Tag;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
    protected ProjectRepository projectRepository;


    @Transactional
    public Project getProject(long id) {
        Optional<Project> result = this.projectRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Project saveProject(Project project) {
        return this.projectRepository.save(project);
    }

  
    @Transactional
    public void deleteProject(Project project) {
        this.projectRepository.delete(project);
    }

    @Transactional
    public Project condividiProgettoConUtente(Project project, User user) {
        project.addUtenteConVisibilità(user);
        user.addProgettoVisibile(project);
        
        return this.projectRepository.save(project);
    }

    @Transactional
	public List<Project> recuperaProgettiDaProprietario(User utente) {
		return this.projectRepository.findByProprietario(utente);
	}
    
    @Transactional
    public Project aggiungiTagAlProgetto(Project project, Tag tag) {
    	project.addTag(tag);
    	return this.projectRepository.save(project);
    
    }
    
    //Metodo che serve per far tornare tutti progetti visibili ad un utente che viene passato come parametro
    public List<Project> getAllProjectsSharedWithMe(User utente){
    	return this.projectRepository.findByUtentiConVisibilità(utente);
    }
    
 

}
