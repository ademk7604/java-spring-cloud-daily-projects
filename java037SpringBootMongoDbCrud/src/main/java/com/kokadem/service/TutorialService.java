package com.kokadem.service;

import com.kokadem.exception.ErrorMessage;
import com.kokadem.exception.ResourceNotFoundException;
import com.kokadem.model.Tutorial;
import com.kokadem.repository.TutorialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialService(TutorialRepository tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    public ResponseEntity<List<Tutorial>> getTutorials(String title) throws ResourceNotFoundException {
        try{
            List<Tutorial> tutorials = new ArrayList<>();

            if(title ==null){
                tutorialRepository.findAll().forEach(tutorials::add);
            }else{
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            }

            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return  ResponseEntity.ok(tutorials);

        }catch(Exception e){
            throw new ResourceNotFoundException(String.format(ErrorMessage.TITLE_NOT_FOUND,title));
        }
    }


    public ResponseEntity<Tutorial> getTutorialById(String id) throws ResourceNotFoundException {

        Optional<Tutorial> tutorialData = Optional.ofNullable(tutorialRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id))));

        if(tutorialData.isPresent()){
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id));
        }

    }


    public ResponseEntity<Tutorial> createTutorial(Tutorial tutorial) throws ResourceNotFoundException {
        try {
            Tutorial savedTutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(savedTutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            throw  new ResourceNotFoundException(String.format(ErrorMessage.TUTORIAL_NOT_RECORDED,tutorial.getTitle()));
        }
    }


//    public ResponseEntity<Tutorial> updateTutorial(String id, Tutorial newTutorial) throws ResourceNotFoundException {
//        Optional<Tutorial> oldTutorial =getTutorialById(id);
//
//
//
//    }
}
