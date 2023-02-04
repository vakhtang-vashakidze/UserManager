package ge.vakhtang.um.service.impl;

import ge.vakhtang.um.entity.RoleEntity;
import ge.vakhtang.um.entity.UserEntity;
import ge.vakhtang.um.repository.UserRepository;
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

import static ge.vakhtang.um.component.utils.Constants.BASIC_ROLE;
import static ge.vakhtang.um.service.exception.ExceptionMessages.USER_NOT_FOUND;

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
        RoleEntity roleEntity = userEntity.getRole();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //TODO refactor later. Role should not be assigned here. Only outside of the scope. Predefined.
        if (roleEntity == null) {
            authorities.add(new SimpleGrantedAuthority(BASIC_ROLE));
        } else {
            authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
