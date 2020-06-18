package it.uniroma3.siw.esame.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.esame.repository.TaskRepository;
import it.uniroma3.siw.esame.model.Commento;
import it.uniroma3.siw.esame.model.Task;

@Service
public class TaskService {
	
	@Autowired
	protected TaskRepository taskRepository;
	
	@Transactional
    public Task getTask(long id) {
        Optional<Task> result = this.taskRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Task saveTask(Task task) {
        return this.taskRepository.save(task);
    }


    @Transactional
    public Task setCompleted(Task task) {
        task.setCompleted(true);
        return this.taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Task task) {
    	//ROMPO I COLLEGAMENTI I VARI TAG
    	task.getListaTag().clear();
        this.taskRepository.delete(task);
    }
    
    @Transactional 
    public Task aggiungiCommentoAlTask(Task task,Commento commento) {
    	task.addCommento(commento);
    	return this.taskRepository.save(task);
    }
    

}
