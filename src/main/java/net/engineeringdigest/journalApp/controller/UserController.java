package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


//    @PostMapping
//    public void createUser(@RequestBody User user){
//        userService.saveUser(user);
//    }

    @PutMapping
    public ResponseEntity<?> updateUsers(@RequestBody User users){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(users.getUserName());
        userInDb.setPassword(users.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        User user = userService.deleteUserByUserName(userName);
        if(user != null){
            return new ResponseEntity<>("Delete Successful", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("User not Found with username..",HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weather = weatherService.getWeather("Bangalore");
        String getUser = "";
        if (weather != null){
            getUser = ", Weather Feels like: " + weather.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hello, " + authentication.getName() + getUser, HttpStatus.OK);
    }
}
