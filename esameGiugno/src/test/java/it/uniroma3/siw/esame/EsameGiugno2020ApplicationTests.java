package it.uniroma3.siw.esame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uniroma3.siw.esame.model.Commento;
import it.uniroma3.siw.esame.model.Credentials;
import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.Tag;
import it.uniroma3.siw.esame.model.Task;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.repository.CommentoRepository;
import it.uniroma3.siw.esame.repository.CredentialsRepository;
import it.uniroma3.siw.esame.repository.ProjectRepository;
import it.uniroma3.siw.esame.repository.TagRepository;
import it.uniroma3.siw.esame.repository.TaskRepository;
import it.uniroma3.siw.esame.repository.UserRepository;
import it.uniroma3.siw.esame.service.CommentoService;
import it.uniroma3.siw.esame.service.CredentialsService;
import it.uniroma3.siw.esame.service.ProjectService;
import it.uniroma3.siw.esame.service.TagService;
import it.uniroma3.siw.esame.service.TaskService;
import it.uniroma3.siw.esame.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class EsameGiugno2020ApplicationTests {

	
	@Autowired
	private CommentoRepository commentoRepository;
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentoService commentoService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	
	
	
	@Before
	public void deleteAll() {
		System.out.println("Deleting all data...\n");
		this.userRepository.deleteAll();
		this.taskRepository.deleteAll();
		this.projectRepository.deleteAll();
		this.tagRepository.deleteAll();
		this.commentoRepository.deleteAll();
		System.out.println("Done!\n");
	}
	
	@Test
	void testServices() {
		
		//Testiamo che vengano salvati due user e un progetto di cui uno è proprietario e l'altro (Lorenzo) ne ha visibilità!
		User user1 = new User("Carlo", "Di Raddo");
		User user2 = new User("Lorenzo", "Donati");
		Credentials c2 = new Credentials("Lollo", "barcolloMaNonMollo");
		Credentials c1 = new Credentials("Carlok98", "Telecomando");
		c1.setUser(user1);
		c2.setUser(user2);
		c1 = this.credentialsService.saveCredentials(c1);
		c2 = this.credentialsService.saveCredentials(c2);
		Project project1 = new Project("Primo Progetto Di Carlo", "Il mio primo progetto");
		project1.setProprietario(user1);
		project1 = this.projectService.saveProject(project1);
		
		assertNotNull(user1.getId());
		assertNotNull(user2.getId());
		assertNotNull(project1.getId());
		
		List<Project> listaProgettiDiCarlo = this.projectService.recuperaProgettiDaProprietario(user1);
		project1 = this.projectService.condividiProgettoConUtente(project1, user2);
		
		List<User> listaUtentiConVisibilità = this.userService.getUtentiConVisibilità(project1);
		assertEquals(listaProgettiDiCarlo.size(), 1);
		assertEquals(listaUtentiConVisibilità.size(), 1);
		
		//Testiamo l'aggiunta di due task al progetto precedentemente creato!
		Task task1 = new Task("PrimoTaskDiCarlo", "Il mio primo task", false);
		Task task2 = new Task("PrimoTaskDiLorenzo", "Il mio primo task", false);
		
		project1.getTasks().add(task1);
		project1.getTasks().add(task2);
		task1.setUtenteAssegnato(user1);
		task2.setUtenteAssegnato(user2);
		
		project1 = this.projectService.saveProject(project1);
		
		Iterator<Task> it = project1.getTasks().iterator();
		task1 = it.next();
		task2 = it.next();
		//Vediamo se è stato generato un id per entrambi i task!
		assertNotNull(task1.getId());
		assertNotNull(task2.getId());
		
		//voglio aggiungere tre commenti ad un task...
		Commento commento1 = new Commento("questo progetto mi aggrada");
		Commento commento2 = new Commento("questo progetto mi aggrada pt2");
		Commento commento3 = new Commento("questo progetto mi aggrada pt3");
		//..li salvo e...
		commento1 = this.commentoService.saveCommento(commento1);
		commento2 = this.commentoService.saveCommento(commento2);
		commento3 = this.commentoService.saveCommento(commento3);
		//...prendo il task e li aggiungo!
		it = project1.getTasks().iterator();
		task1 = it.next();
		task1 = this.taskService.aggiungiCommentoAlTask(task1, commento1);
		task1 = this.taskService.aggiungiCommentoAlTask(task1, commento2);
		task1 = this.taskService.aggiungiCommentoAlTask(task1, commento3);
		//Devono essere 3!
		assertEquals(3,task1.getCommenti().size());
		
		//Creiamo 2 tag...
		Tag tag1 = new Tag("Tag1", "Rosso", "Primo tag del progetto di Carlo");
		Tag tag2 = new Tag("Tag2", "Blu", "Secondo tag del progetto di Carlo");
		//Li aggiungiamo al project di Carlo
		project1 = this.projectService.aggiungiTagAlProgetto(project1, tag1);
		project1 = this.projectService.aggiungiTagAlProgetto(project1, tag2);
		
		tag1 = this.tagRepository.findByNome("Tag1").get(0);
		tag2 = this.tagRepository.findByNome("Tag2").get(0);
		assertNotNull(tag1.getId());
		assertNotNull(tag2.getId());
		
		


	}


	
}
