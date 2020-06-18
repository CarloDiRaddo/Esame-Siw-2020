package it.uniroma3.siw.esame.controller;

import it.uniroma3.siw.esame.controller.session.SessionData;

import it.uniroma3.siw.esame.controller.validation.PasswordValidator;
import it.uniroma3.siw.esame.controller.validation.UserValidator;
import it.uniroma3.siw.esame.model.Credentials;

import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.repository.UserRepository;
import it.uniroma3.siw.esame.service.CredentialsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    CredentialsService credentialsService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SessionData sessionData;
    
    @Autowired
    PasswordValidator passwordValidator;

 
    //Visualizzo la mia Home
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user", loggedUser);
        return "home";
    }

    //Visualizzo le mie informazioni, quali nome, cognome e il mio UserName
    @RequestMapping(value = { "/users/me" }, method = RequestMethod.GET)
    public String me(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        Credentials credentials = sessionData.getLoggedCredentials();
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("credentials", credentials);

        return "userProfile";
    }

 
//    
//    @RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
//    public String admin(Model model) {
//        User loggedUser = sessionData.getLoggedUser();
//        model.addAttribute("user", loggedUser);
//        return "admin";
//    }
    
    
    //Visualizzo la form per Modificare i miei Campi, quali nome e cognome, compresa la Password e...
	@RequestMapping(value = { "/modificaCampi" }, method = RequestMethod.GET)
	public String modificaPass(Model model) {
		Credentials credentials = new Credentials();
		User user = new User();
		model.addAttribute("credentialsForm", credentials);
		model.addAttribute("user", user);
		return "modificaCampiForm";
		
	}
	
	//qui applico le modifiche!
	@RequestMapping(value = { "/modificaCampi" }, method = RequestMethod.POST)
	public String modificaPass(	@ModelAttribute("credentialsForm") Credentials credentials,
								BindingResult credentialsBindingResult,
								@ModelAttribute("user") User user,
								Model model) {
		
			Credentials loggedCredentials = this.sessionData.getLoggedCredentials();
			User loggedUser = this.sessionData.getLoggedUser();
			
			 this.passwordValidator.validate(credentials, credentialsBindingResult);
			 
			 
			 if(!credentialsBindingResult.hasErrors()) {
			loggedCredentials.setPassword(credentials.getPassword());
			loggedUser.setNome(user.getNome());
			loggedUser.setCognome(user.getCognome());
			credentialsService.updateCredentialsPassword(loggedCredentials); // poiche ci sta il cascade, basta fare solo questo
			return "home";
			 }
			
			
			
		return "modificaCampiForm";
		
	}
}
