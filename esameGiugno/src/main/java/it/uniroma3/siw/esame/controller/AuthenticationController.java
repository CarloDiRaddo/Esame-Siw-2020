package it.uniroma3.siw.esame.controller;


import it.uniroma3.siw.esame.controller.validation.CredentialsValidator;
import it.uniroma3.siw.esame.controller.validation.UserValidator;
import it.uniroma3.siw.esame.model.Credentials;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.service.CredentialsService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    CredentialsValidator credentialsValidator;
    
//    @Autowired
//    SessionData sessionData;


    //Restituisce la form per la registrazione dell'utente
    @RequestMapping(value = { "/users/signup" }, method = RequestMethod.GET)
    public String showRegisterForm(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("credentialsForm", new Credentials());

        return "registrazioneUtente";
    }

    //Effettua la registrazione
    @RequestMapping(value = { "/users/signup" }, method = RequestMethod.POST)
    public String registrazioneUtente(@Valid @ModelAttribute("userForm") User user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentialsForm") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        //verifica che sia tutto valido
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        //Se tutto è corretto, allora registra l'utente
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            
            // viene memorizzato anche lo user, grazie ai cascade.
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "registrationSuccessful";
        }
        return "registrazioneUtente";
    }
}