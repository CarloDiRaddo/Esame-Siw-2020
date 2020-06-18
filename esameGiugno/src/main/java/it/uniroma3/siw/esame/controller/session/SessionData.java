package it.uniroma3.siw.esame.controller.session;

import it.uniroma3.siw.esame.model.Credentials;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)//questo oggetto v'à istanziato in ogni sessione differente che ongni utente instaura
public class SessionData {

   
    private User user;

 
    private Credentials credentials;

    @Autowired
    private CredentialsRepository credentialsRepository;

   //OTTENGO LE CREDENZIALI DELL'UTENTE LOGGATO
    public Credentials getLoggedCredentials() {
        if (this.credentials == null)
            this.update();
        return this.credentials;
    }

   
    //OTTENGO L'UTENTE LOGGATO
    public User getLoggedUser() {
        if (this.user == null)
            this.update();
        return this.user;
    }

    //SE PER CASO NON POSSIEDO NE L'UTENTE LOGGATO NE LE SUE CREDENZIALI, ALLORA LE TROVO!
    private void update() {
        UserDetails loggedUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.credentials = this.credentialsRepository.findByUserName(loggedUserDetails.getUsername()).get(); // can never be absent
        this.credentials.setPassword("[PROTECTED]");
        this.user = this.credentials.getUser();
    }
}