package com.example.isaprojekat.service;

import com.example.isaprojekat.model.ConfirmationToken;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
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
    public User save(User student) {
        return userRepository.save(student);
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
        user.setPassword(encodedPassword);
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
}
