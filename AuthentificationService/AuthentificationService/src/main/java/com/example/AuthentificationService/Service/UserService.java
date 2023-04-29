
package com.example.AuthentificationService.Service;
import com.example.AuthentificationService.Entity.User;
import com.example.AuthentificationService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> retrieveAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();

        return users;
    }

    public ResponseEntity<User> retrieveUser(long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<User> addUser(User user) {

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        if (user.getEmail() == null || user.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }


    public ResponseEntity<User> updateUser(long id, User user) {

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user.setId(id);
            userRepository.save(user);
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteUser(long id) {

        userRepository.deleteById(id);
    }


    public boolean existsEmail(String email) {

        return userRepository.existsByEmail(email);
    }
}
