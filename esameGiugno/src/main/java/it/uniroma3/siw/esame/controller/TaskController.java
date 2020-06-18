package it.uniroma3.siw.esame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.esame.controller.session.SessionData;
import it.uniroma3.siw.esame.model.Commento;
import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.Tag;
import it.uniroma3.siw.esame.model.Task;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.service.CommentoService;
import it.uniroma3.siw.esame.service.ProjectService;
import it.uniroma3.siw.esame.service.TaskService;
import it.uniroma3.siw.esame.service.UserService;

@Controller
public class TaskController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CommentoService commentoService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SessionData sessionData;
	
	@Autowired
	private UserService userService;
	
	
	//RESTITUISCE LA FORM PER AGGIUNGERE UN TASK AL PROGETTO
	@RequestMapping(value = {"/aggiungiTask/{projectId}"}, method = RequestMethod.GET)
	public String getAggiungiTaskForm(Model model, @PathVariable Long projectId) {
		Project project=this.projectService.getProject(projectId);
		Task task = new Task();
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		
		return "aggiungiTaskForm";
		
	}
	
	//EFFETTUA L'AGGIUNTA DEL TASK
	@RequestMapping(value = {"/aggiungiTask/{projectId}"}, method = RequestMethod.POST)
	public String aggiungiTask(Model model, @PathVariable Long projectId, @ModelAttribute("task") Task task) {
		
		
		Project project=this.projectService.getProject(projectId);
		if(project.getTasks().contains(task)) {
			return "redirect:/projects/{projectId}";
		}
		project.addTask(task);
		projectService.saveProject(project);
		
		return "redirect:/projects/{projectId}";
		
	}
	
	//ELIMINA IL TASK SELEZIONATO
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/delete"}, method = RequestMethod.GET )
	public String eliminaTask(Model model, @PathVariable Long taskId,@PathVariable Long projectId) {
		
		Task task = this.taskService.getTask(taskId);
		List<Tag> listaTag = task.getListaTag();
		for(Tag tag : listaTag) {
			tag.rimuoviTaskDallaLista(task);
		}
		this.taskService.deleteTask(task);
		
		return "redirect:/projects/{projectId}";
		
	}
	
	//RESTITUISCE LA PAGINA CONTENENTE LE INFORMAZIONI RELATIVE AD UN TASK
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}"} , method = RequestMethod.GET)
	public String visualizzaTask(Model model, @PathVariable Long taskId, @PathVariable Long projectId) {
		Project project = this.projectService.getProject(projectId);
		User proprietario = project.getProprietario();
		User loggedUser = this.sessionData.getLoggedUser();
		Task task = this.taskService.getTask(taskId);
		List<Commento> listaCommenti = task.getCommenti();
		model.addAttribute("listaCommenti",listaCommenti);
		model.addAttribute("projectId", projectId);
		model.addAttribute("proprietario", proprietario);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("task", task);
		
		return "task";
	}
	
	//RESTITUISCE L'ELENCO DEGLI UTENTI CON VISIBILITA' SUL PROGETTO PER ASSEGNARGLI IL TASK 
	@RequestMapping(value = {"{projectId}/assegnaTask/{taskId}"}, method = RequestMethod.GET)
	public String getUtentiPerAssegnareIlTask(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		
		Project project = this.projectService.getProject(projectId);
		List<User> members = project.getUtentiConVisibilità();
		model.addAttribute("members", members);
		model.addAttribute("taskId", taskId);
		model.addAttribute("projectId", projectId);
		
		return "utentiPerAssegnareIlTask";
		
	}
	
	//EFFETTUA L'ASSEGNAZIONE DEL TASK AD UN UTENTE CHE HA VISIBILITA' DEL PROGETTO CUI IL TASK APPARTIENE
	@RequestMapping(value = {"/{projectId}/assegnaTask/{taskId}/{memberId}"}, method = RequestMethod.GET)
	public String getUtentiPerAssegnareIlTask(Model model, @PathVariable Long projectId, @PathVariable Long taskId,
			@PathVariable Long memberId) {
		
		Task task = this.taskService.getTask(taskId);
		User member = this.userService.getUser(memberId);
		task.setUtenteAssegnato(member);
		this.taskService.saveTask(task);
		
		return "redirect:/projects/{projectId}/task/{taskId}";
		
	}
	
	

	//Metodo per modificare un tag
	//GET
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/modifica"} , method = RequestMethod.GET)
	public String modificaTask(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		Task task = taskService.getTask(taskId);
		Project project = projectService.getProject(projectId);
		model.addAttribute("project" , project);
		model.addAttribute("task" , task);
		
		return "modificaTask";
	}
	
	
	//POST
		@RequestMapping(value = { "/projects/{projectId}/task/{taskId}/modifica" }, method = RequestMethod.POST)
		public String modificaTask(@ModelAttribute("task") Task task,
									Model model,@PathVariable Long taskId) {
			
			Task taskAggiornato = taskService.getTask(taskId);
			taskAggiornato.setNome(task.getNome());
			taskAggiornato.setDescrizione(task.getDescrizione());
			
			taskService.saveTask(taskAggiornato);
			
			return "redirect:/home";
		}
		
		
	//Metodo per aggiungere un commento
	//GET
		@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/commento"} , method = RequestMethod.GET)
		public String aggiungiCommentoTask(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
			Task task = taskService.getTask(taskId);
			Project project = projectService.getProject(projectId);
			Commento testoCommento = new Commento();
			model.addAttribute("project" , project);
			model.addAttribute("task" , task);
			model.addAttribute("testoCommento",testoCommento);
			
			
			return "aggiungiCommentoTask";
		}
		
		
		//POST
		@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/commento"} , method = RequestMethod.POST)
		public String aggiungiCommentoTask(@ModelAttribute("testoCommento") Commento testoCommento,
										   @PathVariable Long taskId,
										   Model model) {
			Task task = taskService.getTask(taskId);
			User loggedUser = this.sessionData.getLoggedUser();
			testoCommento.setProprietarioCommento(loggedUser);
			commentoService.saveCommento(testoCommento);
			taskService.aggiungiCommentoAlTask(task, testoCommento);
			
			return "redirect:/projects/{projectId}";
		}
}