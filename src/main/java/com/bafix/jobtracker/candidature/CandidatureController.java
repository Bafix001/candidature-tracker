package com.bafix.jobtracker.candidature;

import org.springframework.http.HttpStatus;
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
    public List<Candidature> getAll() {
        return candidatureService.findAll();
    }

    @GetMapping("/{id}")
    public Candidature getById(@PathVariable Long id) {
        return candidatureService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidature create(@RequestBody Candidature candidature) {
        return candidatureService.create(candidature);
    }

    @PutMapping("/{id}")
    public Candidature update(@PathVariable Long id, @RequestBody Candidature candidature) {
        return candidatureService.update(id, candidature);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        candidatureService.delete(id);
    }
}