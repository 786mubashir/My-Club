package com.club.club_management.security;

import com.club.club_management.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;
    public CustomUserDetailsService(UsersRepository usersRepository){
        this.userRepository =usersRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            return userRepository.findByEmail(email)
                    .orElseThrow(()->
                            new UsernameNotFoundException("User not found")
                    );


    }
}
