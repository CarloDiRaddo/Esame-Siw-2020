package it.uniroma3.siw.esame.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.esame.model.Commento;
import it.uniroma3.siw.esame.repository.CommentoRepository;

@Service
public class CommentoService {
	
	@Autowired
	protected CommentoRepository commentoRepository;
	
	
	@Transactional
	public Commento saveCommento(Commento c) {
		return this.commentoRepository.save(c);
	}
	
	@Transactional
	public Commento getCommento(Long id) {
		Optional<Commento> risultato = this.commentoRepository.findById(id);
		return risultato.orElse(null);
	}
	
	@Transactional
	public void deleteCommento(Commento c) {
		this.commentoRepository.delete(c);
	}
}
