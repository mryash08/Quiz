package org.producer.consumer.quiz.Services;


import lombok.extern.slf4j.Slf4j;
import org.producer.consumer.quiz.Model.User;
import org.producer.consumer.quiz.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;

       Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

        if(checkUsername(username)) {
             user  = repository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        }else {
             user  = repository.findById(Long.valueOf(username))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with Id: " + username));
        }
        user.getAuthorities();
       logger.info("User found with email: " + user.getAuthorities());
       return user;
    }

    public boolean checkUsername(String username){
        return username.contains("@gmail.com");
    }
}