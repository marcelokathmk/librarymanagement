package com.company.librarymanagement.auth.boundary;

import com.company.librarymanagement.auth.control.AuthService;
import com.company.librarymanagement.auth.security.config.SecurityConfig;
import com.company.librarymanagement.server.api.model.LoginApiRequest;
import com.company.librarymanagement.server.api.model.TokenApiResponse;
import com.company.librarymanagement.server.services.AuthApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource implements AuthApi {

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<TokenApiResponse> login(LoginApiRequest loginApiRequest) {
        logger.info("Entering on login endpoint");
        TokenApiResponse response = new TokenApiResponse().token(
                authService.performUserLoginAndReturnJWT(loginApiRequest.getLogin(), loginApiRequest.getPassword()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
