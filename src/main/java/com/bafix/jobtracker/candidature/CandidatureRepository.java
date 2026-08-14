package com.bafix.jobtracker.candidature;

import com.bafix.jobtracker.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    List<Candidature> findByUser(User user);

    Optional<Candidature> findByIdAndUser(Long id, User user);

    List<Candidature> findByUserAndStatut(User user, Statut statut);
}