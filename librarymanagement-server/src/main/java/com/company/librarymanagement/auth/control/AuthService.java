package com.company.librarymanagement.auth.control;

import com.company.librarymanagement.auth.control.repository.UserRepository;
import com.company.librarymanagement.auth.entity.User;
import com.company.librarymanagement.auth.security.jwt.JwtUtil;
import com.company.librarymanagement.shared.decode.Base64Decode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User findUserByLogin(final String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new RuntimeException();
            // lança exception
        }
        return user.get();
    }

    public String performUserLoginAndReturnJWT(String login, String password) {
        User user = findUserByLogin(login);
        if (!password.equals(Base64Decode.decodePassword(user.getPassword()))) {
            // lançar exception
            return null;
        }
        return jwtUtil.generateToken(user.getLogin(), user.getRole());
    }

    public UsernamePasswordAuthenticationToken getAuthUserFromToken(String token) {
        if (jwtUtil.isValidToken(token)) {
            String login = jwtUtil.getLoginFromToken(token);
            User user = findUserByLogin(login);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRoleAsString());
            return new UsernamePasswordAuthenticationToken(user.getLogin(), null, Collections.singletonList(authority));
        }
        // throw exception
        return null;
    }
}
