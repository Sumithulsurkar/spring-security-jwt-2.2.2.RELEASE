package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.entity.AuthRequest;
import com.javatechie.jwt.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * In Postman for http://localhost:9192/
     * Content-Type : application/json
     * Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTY3ODk2OTQ0MiwiaWF0IjoxNjc4OTMzNDQyfQ.c3-YtfgEhMhhta_8U0MAN2PmGYToRNcCN0jVAhc2HZA
     *
     * After Bearer make sure that you are adding the valid & non expired token
     * which was generated from below end point
     * @return
     */
    @GetMapping("/")
    public String welcome() {
        return "Welcome to OffersZone !!";
    }

    /**
     * http://localhost:9192/authenticate
     *
     * Body {
     *     "userName":"user1",
     *     "password": "pwd1"
     * }
     *
     * @param authRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
}
