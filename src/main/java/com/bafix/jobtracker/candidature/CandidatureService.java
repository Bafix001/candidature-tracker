package com.bafix.jobtracker.candidature;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;

    public CandidatureService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    public List<Candidature> findAll() {
        return candidatureRepository.findAll();
    }

    public Candidature findById(Long id) {
        return candidatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable avec l'id " + id));
    }

    public Candidature create(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    public Candidature update(Long id, Candidature candidatureMaj) {
        Candidature existante = findById(id);
        existante.setEntreprise(candidatureMaj.getEntreprise());
        existante.setPoste(candidatureMaj.getPoste());
        existante.setStatut(candidatureMaj.getStatut());
        existante.setDateCandidature(candidatureMaj.getDateCandidature());
        existante.setLienAnnonce(candidatureMaj.getLienAnnonce());
        existante.setNotes(candidatureMaj.getNotes());
        return candidatureRepository.save(existante);
    }

    public void delete(Long id) {
        Candidature existante = findById(id);
        candidatureRepository.delete(existante);
    }
}