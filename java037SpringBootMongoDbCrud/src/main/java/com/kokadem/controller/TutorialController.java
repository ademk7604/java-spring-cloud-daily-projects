package com.kokadem.controller;

import com.kokadem.exception.ResourceNotFoundException;
import com.kokadem.model.Tutorial;
import com.kokadem.service.TutorialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return  tutorialService.getTutorials(title);
    }

    //   http://localhost:8090/api/tutorials/:id
    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) throws ResourceNotFoundException {
        return tutorialService.getTutorialById(id);
    }

    //   http://localhost:8090/api/tutorials
    @PostMapping()
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) throws ResourceNotFoundException {
        return tutorialService.createTutorial(tutorial);
    }

//    @PutMapping("{id}")
//    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @Valid @RequestBody Tutorial newTutorial) throws ResourceNotFoundException {
//        return  tutorialService.updateTutorial(id, newTutorial);
//    }




}
