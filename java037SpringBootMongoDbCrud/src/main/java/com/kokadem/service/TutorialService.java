package com.kokadem.service;

import com.kokadem.exception.ErrorMessage;
import com.kokadem.exception.ResourceNotFoundException;
import com.kokadem.model.Tutorial;
import com.kokadem.repository.TutorialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialService(TutorialRepository tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    public List<Tutorial> getTutorials(String title) throws ResourceNotFoundException {

            List<Tutorial> tutorials = new ArrayList<>();
            if(title ==null){
                tutorialRepository.findAll().forEach(tutorials::add);
            }else{
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            }
        //en sonunda liste kontrolunu yapmam grekir
            if(tutorials.isEmpty()){
                throw new ResourceNotFoundException(String.format(ErrorMessage.TITLE_NOT_FOUND,title));
            }
            return  tutorials;
    }

    public Optional<Tutorial> getTutorialById(String id) throws ResourceNotFoundException {

        Optional<Tutorial> tutorialData = Optional.ofNullable(tutorialRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id))));

        if (tutorialData.isPresent()) {
            return Optional.of(tutorialData.get());
        } else {
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

    public ResponseEntity<Tutorial> updateTutorial(String id, Tutorial tutorial) throws ResourceNotFoundException {
        Optional<Tutorial> oldTutorial =getTutorialById(id);
        if(oldTutorial.isPresent()) {
            Tutorial newTutorial = oldTutorial.get();
            newTutorial.setTitle(tutorial.getTitle());
            newTutorial.setDescription(tutorial.getDescription());
            newTutorial.setPublished(tutorial.isPublished());
            return  new ResponseEntity<>(tutorialRepository.save(newTutorial), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteTutorial(String id) throws ResourceNotFoundException {

        if (!tutorialRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id));
        }
        tutorialRepository.deleteById(id);
    }

    public void deleteTutorials() throws ResourceNotFoundException {
        if(tutorialRepository.count() == 0)
            throw new ResourceNotFoundException(String.format(ErrorMessage.TUTORIAL_NOT_FOUND));

        tutorialRepository.deleteAll();
    }

    public List<Tutorial> findByPublished() throws ResourceNotFoundException {
        List<Tutorial> tutorialList = tutorialRepository.findByPublished(true);
        if(tutorialList.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.TUTORIAL_NOT_FOUND));
        }
        return tutorialList;
    }

}
