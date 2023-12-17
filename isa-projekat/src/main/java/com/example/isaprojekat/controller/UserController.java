package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<User> users = userService.findAll();

        // convert students to DTOs
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }
    @GetMapping("/profile")
    @CrossOrigin(origins = "http://localhost:4200")
    public Object secured(Authentication authentication)
    {

        return authentication.getPrincipal();
    }

    @GetMapping("/loggedUser")
    @CrossOrigin(origins = "http://localhost:4200")
    public User findLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findOneByEmail(name);
        return user;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsersPage(Pageable page) {

        // page object holds data about pagination and sorting
        // the object is created based on the url parameters "page", "size" and "sort"
        Page<User> users = userService.findAll(page);

        // convert students to DTOs
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {

        User user = userService.findOne(id);

        // studen must exist
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserByUsername/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {

        User user = userService.findOneByEmail(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @PutMapping(value= "/updateUser/{username}")
    public ResponseEntity<UserDTO> updateUser (@PathVariable String username, @RequestBody User updatedUser) {
        User exactUser = userService.findOneByEmail(username);

        if (exactUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        exactUser.setFirstName(updatedUser.getFirstName());
        exactUser.setLastName(updatedUser.getLastName());
        exactUser.setCity(updatedUser.getCity());
        exactUser.setCountry(updatedUser.getCountry());
        exactUser.setPhoneNumber(updatedUser.getPhoneNumber());
        exactUser.setOccupation(updatedUser.getOccupation());
        exactUser.setUserRole(updatedUser.getUserRole());

        userService.save(exactUser);

        return new ResponseEntity<>(new UserDTO(exactUser), HttpStatus.OK);
    }

    /*
    @GetMapping(value= "/validatePassword/{newPassword}/{username}")
    public boolean validateUsersPassword(@PathVariable String newPassword, @PathVariable String username) {
        User exactUser = userService.findOneByEmail(username);

        if (exactUser.getPassword().equals(newPassword)) {
            return true;
        }
        else {
            return false;
        }
    }
    */

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO usersDTO) {

        User user = new User();
        user.setFirstName(usersDTO.getFirstName());
        user.setLastName(usersDTO.getLastName());
        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        // a student must exist
        User user = userService.findOne(userDTO.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

        User user = userService.findOne(id);

        if (user != null) {
            userService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findLastName")
    public ResponseEntity<List<UserDTO>> getUsersByLastName(@RequestParam String firstName) {

        List<User> users = userService.findByFirstName(firstName);


        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    //change password

    @GetMapping(value= "/updateUsersPassword/{password}/{userId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<UserDTO> updateUsersPassword (@PathVariable String password, @PathVariable int userId) {
        User foundedUser = userService.findOne(userId);

        if (foundedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        foundedUser.setPassword(password);
        foundedUser.setUserFirstLogged(true);
        userService.save(foundedUser);

        return new ResponseEntity<>(new UserDTO(foundedUser), HttpStatus.OK);
    }
}
