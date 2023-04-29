package com.example.AuthentificationService.Controller;

import com.example.AuthentificationService.DTO.userDTO;
import com.example.AuthentificationService.Entity.User;
import com.example.AuthentificationService.Service.UserService;
import com.example.AuthentificationService.payload.response.JwtResponse;
import com.example.AuthentificationService.security.jwt.JwtUtils;
import com.example.AuthentificationService.security.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oauth")
public class userController {
    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NULL = "ID NULL DETECTED";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    public userController(UserService userService) {
        super();
        this.userService = userService;
    }
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("/getUsers")
    @ResponseBody
    public List<userDTO> getUsers() {
        return userService.retrieveAllUsers().stream().map(user -> modelMapper.map(user, userDTO.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") long id) {
        ResponseEntity<User> user = userService.retrieveUser(id);
        if (user.getStatusCodeValue() == 200) {
            userDTO userDTO = modelMapper.map(user.getBody(), userDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else if(user.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody userDTO userDTO) {
        User userReq = modelMapper.map(userDTO, User.class);
        ResponseEntity<User> user = userService.addUser(userReq);
        if (user.getStatusCodeValue() == 200) {
            userDTO userRes = modelMapper.map(user.getBody(), userDTO.class);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } else if (user.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
        }
    }


    @PutMapping(value = "/User/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") long id, @RequestBody userDTO userDTO) {
        User userReq = modelMapper.map(userDTO, User.class);
        ResponseEntity<User> user = userService.updateUser(id, userReq);

        if (user.getStatusCodeValue() == 200) {
            userDTO userRes = modelMapper.map(user.getBody(), userDTO.class);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } else if (user.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else if(user.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }

    }

    @DeleteMapping(value = "/User/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);}



    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody userDTO usersDTO) {
        User user = modelMapper.map(usersDTO, User.class);
        if (!userService.existsEmail(user.getEmail())) {
            return new ResponseEntity<>(FOUND, HttpStatus.OK);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity<>(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail()
        ), HttpStatus.OK);
    }
}
