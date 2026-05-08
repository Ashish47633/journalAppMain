package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry){
       try{
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String userName = authentication.getName();
           journalEntryService.saveJournalEntry(journalEntry, userName);
           return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> findById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.journalEntryFindById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("No Content found this Id....",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{deleteId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId deleteId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.journalEntryDeleteById(deleteId, userName);
        if(removed){
            return new ResponseEntity<>("Delete Successfully..", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Content found this is Id...", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{updateId}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable ObjectId updateId,
            @RequestBody JournalEntry newEntry
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(updateId))
                .toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.journalEntryFindById(updateId);
            if(journalEntry.isPresent()){
                JournalEntry oldEntry = journalEntry.get();
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : journalEntry.get().getTitle() );
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : journalEntry.get().getContent());
                journalEntryService.saveJournalEntry(oldEntry);
                return new ResponseEntity<>(journalEntry,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("User Not Found this id : "+ updateId, HttpStatus.NOT_FOUND);
    }

}
