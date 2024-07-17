package com.kokadem.controller;

import com.kokadem.exception.ResourceNotFoundException;
import com.kokadem.model.Tutorial;
import com.kokadem.service.TutorialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//CrossOrigin(origins = "http://localhost:8081")

// http://localhost:8090/api
@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

    private final TutorialService tutorialService;

    public TutorialController(TutorialService tutorialService){
        this.tutorialService = tutorialService;
    }

    //   http://localhost:8090/api/tutorials
    @GetMapping()
    public ResponseEntity<List<Tutorial>> getTutorials(@RequestParam(required = false) String title) throws ResourceNotFoundException {
        List<Tutorial> tutorialList = tutorialService.getTutorials(title);
        return  ResponseEntity.ok(tutorialList);
    }

    //   http://localhost:8090/api/tutorials/:id
    @GetMapping("/{id}")
    public Optional<Tutorial> getTutorialById(@PathVariable("id") String id) throws ResourceNotFoundException {
        return tutorialService.getTutorialById(id);
    }

    //   http://localhost:8090/api/tutorials
    @PostMapping()
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) throws ResourceNotFoundException {
        return tutorialService.createTutorial(tutorial);
    }
    //   http://localhost:8090/api/tutorials/:id
    @PutMapping("{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @Valid @RequestBody Tutorial tutorial) throws ResourceNotFoundException {
        return  tutorialService.updateTutorial(id, tutorial);
    }

    //   http://localhost:8090/api/tutorials/:id
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTutorial(@PathVariable("id") String id) throws ResourceNotFoundException {
        tutorialService.deleteTutorial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //   http://localhost:8090/api/tutorials
    @DeleteMapping()
    public ResponseEntity<Void> deleteTutorials() throws ResourceNotFoundException {
        tutorialService.deleteTutorials();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //   http://localhost:8090/api/tutorials/published
    @GetMapping("/published")
    public ResponseEntity<List<Tutorial>> findByPublished() throws ResourceNotFoundException {
        List<Tutorial> tutorialList = tutorialService.findByPublished();
        return ResponseEntity.ok(tutorialList);
    }




}
