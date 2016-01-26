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

/**
 * Created by carlos13 on 1/25/16.
 */

@RestController
@RequestMapping(value = '/users')
class UserController {
    @Inject
    def UserRepository userRepository

    @Inject
    def AuthenticationManager authenticationManager

    @RequestMapping(method = RequestMethod.GET)
    def List<User> list() {
        return userRepository.findAll()
    }

    @RequestMapping(method = RequestMethod.POST)
    def User create(@RequestBody User user) {
        user.setPasswordHash(new BCryptPasswordEncoder().encode(user.getPassword()))
        return userRepository.save(user)
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    def void login(@RequestBody User user) {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(user.emailAddress, user.password)

        // Authenticate the user
        def authentication = authenticationManager.authenticate(authRequest)
        SecurityContext securityContext = SecurityContextHolder.getContext()
        securityContext.setAuthentication(authentication)

    }
}
