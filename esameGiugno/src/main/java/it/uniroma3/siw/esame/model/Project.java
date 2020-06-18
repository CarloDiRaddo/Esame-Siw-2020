package it.uniroma3.siw.esame.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	
	private String descrizione;
	
	@Column(updatable = false, nullable = false)
	private LocalDate dataInizio;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Set<Task> tasks;
	
	@ManyToOne
	private User proprietario;
	
	@ManyToMany
	private List<User> utentiConVisibilità;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ProjectRiferito")
	private List<Tag> listaTag;

						
	@PrePersist
	public void inizializzaDataInizio() {
		this.dataInizio = LocalDate.now();
	}
	
	public Project() {
		this.tasks = new HashSet<>();
		this.utentiConVisibilità = new ArrayList<>();
		this.listaTag = new ArrayList<>();
	}
	
	public Project(String nome, String descrizione) {
		this.tasks = new HashSet<>();
		this.listaTag = new ArrayList<>();
		this.utentiConVisibilità = new ArrayList<>();
		this.nome= nome;
		this.descrizione = descrizione;
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

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> attività) {
		this.tasks = attività;
	}

	public User getProprietario() {
		return proprietario;
	}

	public void setProprietario(User proprietario) {
		this.proprietario = proprietario;
	}

	public List<User> getUtentiConVisibilità() {
		return utentiConVisibilità;
	}

	public void setUtentiConVisibilità(List<User> utentiConVisibilità) {
		this.utentiConVisibilità = utentiConVisibilità;
	}
	
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return this.descrizione;
	}
	
	public List<Tag> getListaTag() {
		return listaTag;
	}

	public void setListaTag(List<Tag> listaTag) {
		this.listaTag = listaTag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataInizio == null) ? 0 : dataInizio.hashCode());
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
		Project other = (Project) obj;
		if (dataInizio == null) {
			if (other.dataInizio != null)
				return false;
		} else if (!dataInizio.equals(other.dataInizio))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public void addUtenteConVisibilità(User user) {
		  if (!this.utentiConVisibilità.contains(user))
	            this.utentiConVisibilità.add(user);
		
	}
	
	public void addTag(Tag t) {
		this.listaTag.add(t);
	}
	
	
	public void addTask(Task t) {
		this.tasks.add(t);
	}
}
