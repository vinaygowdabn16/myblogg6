package com.blopapii.controller;

import com.blopapii.entity.Role;
import com.blopapii.entity.User;
import com.blopapii.payload.JWTAuthResponse;
import com.blopapii.payload.LoginDto;
import com.blopapii.payload.SignUpDto;
import com.blopapii.repository.RoleRepository;
import com.blopapii.repository.UserRepository;
import com.blopapii.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @PostMapping("/signUp")
    public ResponseEntity<?> userRegister(@RequestBody SignUpDto signUpDto){

    if(userRepository.existsByEmail(signUpDto.getEmail())){

        return new ResponseEntity<>("email is already exist", HttpStatus.BAD_REQUEST);
    }

    if(userRepository.existsByUsername(signUpDto.getUsername())){
        return new ResponseEntity<>("username is already exist",HttpStatus.BAD_REQUEST);
    }

    User user = new User();
    user.setName(signUpDto.getName());
    user.setEmail(signUpDto.getEmail());
    user.setUsername(signUpDto.getUsername());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(Collections.singleton(role));

       userRepository.save(user);
          return new ResponseEntity<>("user register successfully",HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

}
