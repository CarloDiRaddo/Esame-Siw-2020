package it.uniroma3.siw.esame.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;

	private String colore;

	private String descrizione;

	@ManyToMany
	private List<Task> listaTask;
	
	
	public Tag() {
		this.listaTask = new ArrayList<>();
	}
	
	public Tag(String nome, String colore , String descrizione) {
		this.listaTask = new ArrayList<>();
		this.nome=nome;
		this.colore = colore;
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

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colore == null) ? 0 : colore.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	
	

	public List<Task> getListaTask() {
		return listaTask;
	}

	public void setListaTask(List<Task> listaTask) {
		this.listaTask = listaTask;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (colore == null) {
			if (other.colore != null)
				return false;
		} else if (!colore.equals(other.colore))
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

	public void addTask(Task task) {
	this.listaTask.add(task);
		
	}

	public void rimuoviTaskDallaLista(Task task) {
		this.listaTask.remove(task);
		
	}

}
