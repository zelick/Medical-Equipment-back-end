package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.UserDTO;
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
    public User save(UserDTO userRequest) {
        User existingUser = userRepository.findUserByEmail(userRequest.getEmail());

        if (existingUser != null) {
            // Update existing user details
            existingUser.setFirstName(userRequest.getFirstName());
            existingUser.setLastName(userRequest.getLastName());
            existingUser.setCity(userRequest.getCity());
            existingUser.setCountry(userRequest.getCountry());
            existingUser.setPhoneNumber(userRequest.getPhoneNumber());
            existingUser.setOccupation(userRequest.getOccupation());
            existingUser.setCompanyInfo(userRequest.getCompanyInfo());

            // Save the updated user
            return userRepository.save(existingUser);
        }
        User u = new User();
        u.setUsername(userRequest.getEmail());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        u.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setEnabled(false);
        u.setEmail(userRequest.getEmail());
        u.setPenaltyPoints(0.0);
        u.setCity(userRequest.getCity());
        u.setCountry(userRequest.getCountry());
        u.setOccupation(userRequest.getOccupation());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setCompanyInfo(userRequest.getCompanyInfo());

        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        List<Role> roles = roleService.findByName("ROLE_USER");
        u.setRoles(roles);

        return this.userRepository.save(u);
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
        UserDTO u = new UserDTO(user);
        User savedUser = save(u);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                savedUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
