package it.uniroma3.siw.esame.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Task {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String nome;
	
	private String descrizione;
	
	//SOLO gli utenti che hanno visibilita sul progetto cui il task appartiene possono scrivere commenti!
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "TaskRelativo")
	private List<Commento> commenti;
	
	@Column(updatable = false, nullable=false)
	private LocalDate dataCreazione;
	
	@PrePersist
	public void inizializzaCreazione() {
		this.dataCreazione = LocalDate.now();
	}
	
	@ManyToOne
	//utente tra quelli che hanno visibilità sul progetto cui appartiene il task
	private User utenteAssegnato;
	
	@ManyToMany(mappedBy = "listaTask", fetch = FetchType.EAGER)
	//associazione ad uno o più tag del progetto cui il Task appartiene
	private List<Tag> listaTag;


	@Column(nullable = false)
	//Per marcare come completato un task
	private boolean completed;
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
		
	}


	public Task() {
		this.listaTag = new ArrayList<>();
		this.commenti = new ArrayList<>();
		
	}
	
	public Task(String nome, String descrizione, boolean completato) {
		this.listaTag = new ArrayList<>();
		this.commenti = new ArrayList<>();
		this.nome = nome;
		this.descrizione= descrizione;
		this.completed = completato;
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void addCommento(Commento commento) {
		this.getCommenti().add(commento);
	}
	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public List<Tag> getListaTag() {
		return listaTag;
	}

	public void setListaTag(List<Tag> listaTag) {
		this.listaTag = listaTag;
	}
	
	public void addTag(Tag t) {
		this.listaTag.add(t);
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public User getUtenteAssegnato() {
		return utenteAssegnato;
	}

	public void setUtenteAssegnato(User utenteAssegnato) {
		this.utenteAssegnato = utenteAssegnato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (completed != other.completed)
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
