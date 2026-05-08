package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.repsitory.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

   @Test
   public void testSaveNewUser(){
        userRepository.getUserForSentimentAnalysis();
    }
}
