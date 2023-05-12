package bbudzowski.homeautoserver.security;

import bbudzowski.homeautoserver.repositories.UserRepository;
import bbudzowski.homeautoserver.tables.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService, UserDetailsManager {

    private final UserRepository userRepository = new UserRepository();

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }


    @Override
    public void createUser(UserDetails user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        userRepository.addUser(new UserEntity(user.getUsername(), password));
    }

    @Override
    public void updateUser(UserDetails user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        userRepository.changePassword(user.getUsername(), password);
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
