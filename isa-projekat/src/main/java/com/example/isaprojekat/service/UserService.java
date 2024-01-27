package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.ConfirmationToken;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.isaprojekat.model.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    public User findOne(Integer id) {
        return userRepository.findById(id).orElseGet(null);
    }
    public User findOneByEmail(String email){return userRepository.findUserByEmail(email);}
    public List<User> findByFirstName(String firstName) {
        return userRepository.findAllByFirstName(firstName);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User with email" + email +  "not found!"));
    }
    public String signUpUser(User user){
        boolean userExists = userRepository.
                findByEmail(user.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already exists");
        }
        String encodedPassword =
                bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(user.getPassword());
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
    public User create(User user){
        boolean userExists = userRepository.
                findByEmail(user.getEmail())
                .isPresent();
        if(userExists) {
            throw new IllegalStateException("email already exists");
        }
        return userRepository.save(user);
    }
    public boolean isUser(Integer userId){
        User loggedInUser = findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER) {
                return false;
            }
        } else {return false;}
        return true;
    }

    public boolean isCompanyAdmin(Integer userId){
        User loggedInUser = findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.COMPANY_ADMIN) {
                return false;
            }
        } else {return false;}
        return true;
    }

    public boolean isSystemAdmin(Integer userId){
        User loggedInUser = findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.SYSTEM_ADMIN) {
                return false;
            }
        } else {return false;}
        return true;
    }

}
