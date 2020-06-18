package it.uniroma3.siw.esame.model;

import javax.persistence.*;

@Entity
public class Commento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String commento;
	
	@ManyToOne
	private User proprietarioCommento;
	
	public Commento() {}
	
	public Commento(String testo) {
		this.commento=testo;
	}
	

	public User getProprietarioCommento() {
		return proprietarioCommento;
	}

	public void setProprietarioCommento(User proprietarioCommento) {
		this.proprietarioCommento = proprietarioCommento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}
	
	@Override
	public String toString() {
		return this.commento.toString();
	}
	
	
}
