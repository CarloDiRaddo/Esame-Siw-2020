package it.uniroma3.siw.esame.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.model.Tag;
import it.uniroma3.siw.esame.model.Task;
import it.uniroma3.siw.esame.service.ProjectService;
import it.uniroma3.siw.esame.service.TagService;
import it.uniroma3.siw.esame.service.TaskService;

@Controller
public class TagController {

	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TagService tagService;
	
	
	
	//FORM PER AGGIUNGERE UN TAG AD UN MIO PROGETTO
	@RequestMapping(value = {"/formTag/{projectId}"}, method = RequestMethod.GET)
	public String getFormTag(Model model, @PathVariable Long projectId) {
		Tag tag = new Tag();
		Project project = this.projectService.getProject(projectId);
		model.addAttribute("project", project);
		model.addAttribute("tag", tag);
		return "aggiungiTagForm";

	}
	
	//AGGIUNTA TAG AL PROGETTO
	@RequestMapping(value = {"/formTag/{projectId}"}, method = RequestMethod.POST)
	public String aggiungiTag(Model model, @PathVariable Long projectId, @ModelAttribute("tag") Tag tag) {
		
		Project project = this.projectService.getProject(projectId);
		
		if(project.getListaTag().contains(tag)) {
			return "redirect:/projects/{projectId}";
		}
		this.projectService.aggiungiTagAlProgetto(project, tag);
	
		return "redirect:/projects/{projectId}";

	}
	
	//OTTENIAMO LA LISTA DEI TAG DEL PROGETTO CUI IL TASK APPARTIENE E...
	@RequestMapping(value = {"{projectId}/assegnaTag/{taskId}"}, method = RequestMethod.GET)
	public String getAggiungiTagAlTask(Model model, @PathVariable Long projectId ,@PathVariable Long taskId) {
		
		
		Project project = this.projectService.getProject(projectId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("project", project); 
		
		return "listaTagDelProgetto";
		
	}
	
	//...ASSEGNAMO DEFINITIVAMENTE IL TAG AL TASK
	@RequestMapping(value = {"{projectId}/assegnaTag/{taskId}/{tagId}"}, method = RequestMethod.GET)
	public String aggiungiTagAlTask(Model model ,@PathVariable Long taskId, @PathVariable Long tagId,
			@PathVariable Long projectId) {
		
		Task task = this.taskService.getTask(taskId);
		Tag tag = this.tagService.getTag(tagId);
		if(task.getListaTag().contains(tag)) {
			return "redirect:/home";
		}
		task.addTag(tag);  //CREO I COLLEGAMENTI!!!
		tag.addTask(task);
		this.tagService.saveTag(tag);  //Li Salvo entrambi poiche non abbiamo messo i cascade!!!
		this.taskService.saveTask(task);
		return "redirect:/home";
		
	}
}
