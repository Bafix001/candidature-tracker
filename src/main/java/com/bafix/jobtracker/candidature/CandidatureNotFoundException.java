package com.bafix.jobtracker.candidature;

public class CandidatureNotFoundException extends RuntimeException {

    public CandidatureNotFoundException(Long id) {
        super("Candidature introuvable avec l'id " + id);
    }
}