package ge.vtt.um.service.impl;

import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

import static ge.vtt.um.service.exception.ExceptionMessages.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException(USER_NOT_FOUND.getMessage());
        }
        UserEntity userEntity = userRepository.getUserEntityByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
