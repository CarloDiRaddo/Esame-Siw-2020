package it.uniroma3.siw.esame.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.esame.model.Tag;
import it.uniroma3.siw.esame.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	protected TagRepository tagRepository;
	
	@Transactional
	public Tag saveTag(Tag t) {
		return this.tagRepository.save(t);
	}
	
	@Transactional
	public Tag getTag(Long id) {
		Optional<Tag> risultato = this.tagRepository.findById(id);
		return risultato.orElse(null);
	}
	
	@Transactional
	public void deleteTag(Tag t) {
		this.tagRepository.delete(t);
	}
	
}
