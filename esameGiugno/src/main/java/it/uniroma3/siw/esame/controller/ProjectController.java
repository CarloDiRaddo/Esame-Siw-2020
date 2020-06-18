package it.uniroma3.siw.esame.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.esame.controller.session.SessionData;
import it.uniroma3.siw.esame.controller.validation.CredentialsValidator;
import it.uniroma3.siw.esame.controller.validation.ProjectValidator;
import it.uniroma3.siw.esame.model.Credentials;
import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.Tag;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.service.CredentialsService;
import it.uniroma3.siw.esame.service.ProjectService;
import it.uniroma3.siw.esame.service.TagService;
import it.uniroma3.siw.esame.service.UserService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ProjectValidator projectValidator;
	
	@Autowired
	private SessionData sessionData;
	
	@Autowired
	private UserService userService;

	

	//Metodo che mostra i progetti dove l'utente loggato è proprietario
	//GET
	@RequestMapping(value = { "/projects" }, method = RequestMethod.GET)
	public String myOwnedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectList = projectService.recuperaProgettiDaProprietario(loggedUser);
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("projectList",projectList);
	return "myOwnedProjects";
	}
	
	//VISUALIZZO UN MIO PROGETTO TRAMITE ID
	@RequestMapping(value = { "/projects/{projectId}" }, method = RequestMethod.GET)
	public String project(Model model,
							@PathVariable Long projectId) {
		Project project = projectService.getProject(projectId);
		User loggedUser = this.sessionData.getLoggedUser();
		if(project == null )
			return "redirect:/home";
		List<User> members = userService.getUtentiConVisibilità(project);
		
		//PER SICUREZZA (SE L'UTENTE LOGGATO CERCA DI ACCEDERE DA URL AD UN PROGETTO SU CUI NON HA VISIBILITA O DI CUI NON E' PROPRIETARIO) 
		if(project.getUtentiConVisibilità().contains(loggedUser) ||project.getProprietario().equals(loggedUser)) {
		User proprietario = project.getProprietario();
		model.addAttribute("project",project);
		model.addAttribute("members",members);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("proprietario", proprietario);
		
	return "project";	}
		
		return "redirect:/home";
	}
	
	//metodi per creare un nuovo progetto
	//GET
	@RequestMapping(value = { "/projects/add" }, method = RequestMethod.GET)
	public String createProject(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("projectForm", new Project());
		return "addProject";
	}
	
	//POST
	@RequestMapping(value = { "/projects/add" }, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("projectForm") Project project,
								BindingResult projectBindingResult,
								Model model) {
		User loggedUser = sessionData.getLoggedUser();
		
		this.projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {
			project.setProprietario(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId();
		}
		model.addAttribute("loggedUser",loggedUser);
		return "addProject";
	}
	
	
	//Metodo per mostrare tutti i progetti visibili ad un utente
	@RequestMapping(value = { "/users/visibleProjects" }, method = RequestMethod.GET)
	public String project(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projects = projectService.getAllProjectsSharedWithMe(loggedUser);
		
		
		
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("projects",projects);
		
	return "myVisibleProjects";	
	}
	
	
	//Metodo per condividere un progetto con un altro utente
	//GET
	@RequestMapping(value = { "/projects/{projectId}/shareWith" }, method = RequestMethod.GET)
	public String shareWith(Model model, @PathVariable Long projectId) {
		Project project = projectService.getProject(projectId);
		model.addAttribute("project",project);
		model.addAttribute("credentialsForm",new Credentials());
	
		
	return "shareProject";	
	}
	
	//POST
	@RequestMapping(value = { "/projects/{projectId}/shareWith" }, method = RequestMethod.POST)
	public String shareWith(@ModelAttribute("credentialsForm") Credentials credentials,
								@ModelAttribute("project") Project project,
								Model model,@PathVariable Long projectId) {
		
		
		User loggedUser = sessionData.getLoggedUser();
		System.out.println(credentials.getUserName());
		Credentials credenziali = this.credentialsService.getCredentials(credentials.getUserName());
		if(credenziali == null) {
			return "redirect:/projects/{projectId}/shareWith";
		}
		
		project = projectService.getProject(projectId);
		User utente = credenziali.getUser();
		
		if(loggedUser.equals(utente)) {
		return "redirect:/projects/{projectId}/shareWith";
		}
		projectService.condividiProgettoConUtente(project, utente);
		model.addAttribute(loggedUser);
		
		return "redirect:/home";
	}
	

	//ELIMINAZIONE DI UN MIO PROGETTO
	@RequestMapping(value = {"/projects/{projectId}/delete"}, method = RequestMethod.GET)
	public String eliminaProgetto(@PathVariable Long projectId, Model model) {
		Project project = this.projectService.getProject(projectId);
		if(project.getProprietario().equals(this.sessionData.getLoggedUser())) { //PER SICUREZZA FACCIAMO LA VERIFICA DEL PROPRIETARIO
			this.projectService.deleteProject(project);
			return "redirect:/projects";
		}
		
		return "redirect:/projects";
	}
	
	//Metodo per aggiornare i dati di un mio progetto
	//GET
	@RequestMapping(value = { "/projects/{projectId}/modifica" }, method = RequestMethod.GET)
	public String modificaProject(Model model, @PathVariable Long projectId) {
		Project project = projectService.getProject(projectId);
		model.addAttribute("project",project);
		
	return "modificaProject";	
	}
	
	
	//POST
	@RequestMapping(value = { "/projects/{projectId}/modifica" }, method = RequestMethod.POST)
	public String modificaProject(@ModelAttribute("modificaProjectForm") Project modificaProjectForm,
								Model model,@PathVariable Long projectId) {
		
		Project project = projectService.getProject(projectId);
		project.setNome(modificaProjectForm.getNome());
		project.setDescrizione(modificaProjectForm.getDescrizione());
		
		projectService.saveProject(project);
		
		return "redirect:/home";
	}

}