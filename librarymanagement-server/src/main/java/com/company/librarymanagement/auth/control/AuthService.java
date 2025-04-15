package com.company.librarymanagement.auth.control;

import com.company.librarymanagement.auth.control.exception.AuthException;
import com.company.librarymanagement.auth.control.repository.UserRepository;
import com.company.librarymanagement.auth.entity.User;
import com.company.librarymanagement.auth.security.jwt.JwtUtil;
import com.company.librarymanagement.shared.decode.Base64Decode;
import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import com.company.librarymanagement.shared.exception.LibraryException;
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
            throw new AuthException(LibraryErrorMessage.AUTH_USER_NOT_FOUND);
        }
        return user.get();
    }

    public String performUserLoginAndReturnJWT(String login, String password) {
        User user = findUserByLogin(login);
        if (!password.equals(Base64Decode.decodePassword(user.getPassword()))) {
            throw new AuthException(LibraryErrorMessage.AUTH_PASSWORD_INCORRECT);
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
        throw new AuthException(LibraryErrorMessage.AUTH_INVALID_TOKEN);
    }
}
