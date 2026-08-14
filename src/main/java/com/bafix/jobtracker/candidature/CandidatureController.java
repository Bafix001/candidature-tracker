package com.bafix.jobtracker.candidature;

import com.bafix.jobtracker.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidatures")
public class CandidatureController {

    private final CandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @GetMapping
    public List<Candidature> getAll(@AuthenticationPrincipal User currentUser) {
        return candidatureService.findAllForUser(currentUser);
    }

    @GetMapping("/{id}")
    public Candidature getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        return candidatureService.findByIdForUser(id, currentUser);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidature create(@RequestBody Candidature candidature, @AuthenticationPrincipal User currentUser) {
        return candidatureService.create(candidature, currentUser);
    }

    @PutMapping("/{id}")
    public Candidature update(@PathVariable Long id, @RequestBody Candidature candidature, @AuthenticationPrincipal User currentUser) {
        return candidatureService.update(id, candidature, currentUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        candidatureService.delete(id, currentUser);
    }
}