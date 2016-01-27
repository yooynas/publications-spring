package com.carlospaelinck.controllers

import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping(value = '/users')
class UserController {
    @Inject
    UserRepository userRepository

    @Inject
    AuthenticationManager authenticationManager

    @RequestMapping(method = RequestMethod.GET)
    List<User> list() {
        return userRepository.findAll()
    }

    @RequestMapping(method = RequestMethod.POST)
    User create(@RequestBody User user) {
        user.setPasswordHash(new BCryptPasswordEncoder().encode(user.getPassword()))
        return userRepository.save(user)
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    void login(@RequestBody User user) {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(user.emailAddress, user.password)

        // Authenticate the user
        def authentication = authenticationManager.authenticate(authRequest)
        SecurityContext securityContext = SecurityContextHolder.getContext()
        securityContext.setAuthentication(authentication)

    }
}
