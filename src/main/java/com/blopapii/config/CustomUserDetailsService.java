package com.blopapii.config;

import com.blopapii.entity.Role;
import com.blopapii.entity.User;
import com.blopapii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(
                        ()-> new UsernameNotFoundException("username not found with this  "+usernameOrEmail)
                );


        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),mapToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapToAuthorities(Set<Role> roles){

      return  roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
