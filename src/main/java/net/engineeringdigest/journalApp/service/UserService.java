package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repsitory.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;



    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Exception occurred while saving user ");
            log.info("Exception occurred while saving user: ");
            log.warn("Exception occurred while saving user: ");
            log.debug("Exception occurred while saving user: ");
            return false;
        }
    }

    public void saveAdminUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> userFindById(ObjectId id){
        return userRepository.findById(id);
    }

    public void userDeleteById(ObjectId deleteId){
        userRepository.deleteById(deleteId);
    }



    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User deleteUserByUserName(String userName){
        User user = userRepository.deleteByUserName(userName);
        if(user != null){
            userRepository.delete(user);
            return user;
        }
        return null;
    }

}
