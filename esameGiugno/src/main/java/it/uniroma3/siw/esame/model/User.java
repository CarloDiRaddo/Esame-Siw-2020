package it.uniroma3.siw.esame.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	@Column(updatable = false, nullable = false)
	private LocalDateTime dataCreazione;
	
	 @Column(nullable = false)
	  	private LocalDateTime dataUltimaModifica;
	
	@PrePersist
	public void inizializzaCreazione() {
		this.dataCreazione = LocalDateTime.now();
		this.dataUltimaModifica = LocalDateTime.now();
	}
	
	@OneToMany(mappedBy = "proprietario")
	private List<Project> progettiPropri;
	
	@ManyToMany(mappedBy = "utentiConVisibilità")
	private List<Project> progettiVisibili;

	 
	 @PreUpdate
	    protected void onUpdate() {
	        this.dataUltimaModifica = LocalDateTime.now();
	    }
	
	public User() {
		this.progettiPropri = new ArrayList<Project>();
		this.progettiVisibili = new ArrayList<Project>();
	}
	
	public User(String nome, String cognome) {
		this.progettiPropri = new ArrayList<Project>();
		this.progettiVisibili = new ArrayList<Project>();
		this.nome=nome;
		this.cognome = cognome;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public List<Project> getProgettiPropri() {
		return progettiPropri;
	}

	public void setProgettiPropri(List<Project> progettiPropri) {
		this.progettiPropri = progettiPropri;
	}

	public List<Project> getProgettiVisibili() {
		return progettiVisibili;
	}

	public void setProgettiVisibili(List<Project> progettiVisibili) {
		this.progettiVisibili = progettiVisibili;
	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public void addProgettoVisibile(Project project) {
		if(!this.progettiVisibili.contains(project))
		this.progettiVisibili.add(project);
	}
}
