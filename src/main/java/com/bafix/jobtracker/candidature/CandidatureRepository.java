package com.bafix.jobtracker.candidature;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    List<Candidature> findByStatut(Statut statut);

}