package com.bafix.jobtracker.candidature;

import com.bafix.jobtracker.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;

    public CandidatureService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    public List<Candidature> findAllForUser(User user) {
        return candidatureRepository.findByUser(user);
    }

    public Candidature findByIdForUser(Long id, User user) {
        return candidatureRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new CandidatureNotFoundException(id));
    }

    public Candidature create(Candidature candidature, User user) {
        candidature.setUser(user);
        return candidatureRepository.save(candidature);
    }

    public Candidature update(Long id, Candidature candidatureMaj, User user) {
        Candidature existante = findByIdForUser(id, user);
        existante.setEntreprise(candidatureMaj.getEntreprise());
        existante.setPoste(candidatureMaj.getPoste());
        existante.setStatut(candidatureMaj.getStatut());
        existante.setDateCandidature(candidatureMaj.getDateCandidature());
        existante.setLienAnnonce(candidatureMaj.getLienAnnonce());
        existante.setNotes(candidatureMaj.getNotes());
        return candidatureRepository.save(existante);
    }

    public void delete(Long id, User user) {
        Candidature existante = findByIdForUser(id, user);
        candidatureRepository.delete(existante);
    }
}