package com.bafix.jobtracker.candidature;

import com.bafix.jobtracker.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidatureServiceTest {

    @Mock
    private CandidatureRepository candidatureRepository;

    @InjectMocks
    private CandidatureService candidatureService;

    @Test
    void findAllForUser_retourneLesCandidaturesDeLutilisateur() {
        User user = new User();
        user.setId(1L);

        Candidature candidature = new Candidature();
        candidature.setId(10L);
        candidature.setEntreprise("Capgemini");

        when(candidatureRepository.findByUser(user)).thenReturn(List.of(candidature));

        List<Candidature> resultat = candidatureService.findAllForUser(user);

        assertThat(resultat).hasSize(1);
        assertThat(resultat.get(0).getEntreprise()).isEqualTo("Capgemini");
    }

    @Test
    void findByIdForUser_leveUneExceptionSiIntrouvable() {
        User user = new User();
        user.setId(1L);

        when(candidatureRepository.findByIdAndUser(99L, user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidatureService.findByIdForUser(99L, user))
                .isInstanceOf(CandidatureNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_associeLaCandidatureALutilisateurEtLaSauvegarde() {
        User user = new User();
        user.setId(1L);

        Candidature candidature = new Candidature();
        candidature.setEntreprise("Capgemini");
        candidature.setPoste("Développeur Java");
        candidature.setStatut(Statut.ENVOYEE);
        candidature.setDateCandidature(LocalDate.now());

        when(candidatureRepository.save(candidature)).thenReturn(candidature);

        Candidature resultat = candidatureService.create(candidature, user);

        assertThat(resultat.getUser()).isEqualTo(user);
        verify(candidatureRepository).save(candidature);
    }
}