package it.uniroma3.siw.esame.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.esame.model.Project;
import it.uniroma3.siw.esame.service.ProjectService;

//VALIDATORE DEL PROGETTO
@Component
public class ProjectValidator implements Validator {
	
    final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;
    final Integer MAX_DESCRIPTION_LENGHT = 1000;
	
	@Autowired
	ProjectService projectService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Project project = (Project) target;
        String nome = project.getNome().trim();
        String descrizione = project.getDescrizione().trim();
        
        

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");
		
        if (descrizione.length() > MAX_DESCRIPTION_LENGHT)
        	errors.rejectValue("descrizione", "size");
	}

}
