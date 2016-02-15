package com.carlospaelinck.controllers

import com.carlospaelinck.domain.User
import com.carlospaelinck.exceptions.ConflictException
import com.carlospaelinck.repositories.UserRepository
import com.carlospaelinck.security.PublicationsUserDetails
import com.carlospaelinck.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = '/users')
class UserController {
    @Inject
    UserService userService

    @RequestMapping(value = '/current', method = RequestMethod.GET)
    User current(@AuthenticationPrincipal PublicationsUserDetails userDetails) {
        return userService.get(userDetails.user.emailAddress)
    }

    @RequestMapping(method = RequestMethod.POST)
    User create(@RequestBody User user) {
        return userService.create(user)
    }

    @RequestMapping(method = RequestMethod.PUT)
    User update(HttpServletRequest request, @RequestBody User user, @AuthenticationPrincipal PublicationsUserDetails userDetails) {
        if (userDetails.user.temporary && userService.get(user.emailAddress) != null) {
            throw new ConflictException()

        } else {
            def updatedUser = userService.update(user)
            userDetails.user = updatedUser
            return updatedUser
        }
    }

    @RequestMapping(value = '/login', method = RequestMethod.POST)
    User login(@RequestBody User user) {
        return userService.login(user)
    }

    @RequestMapping(value = '/logout', method = RequestMethod.POST)
    def logout(HttpServletRequest request, @AuthenticationPrincipal PublicationsUserDetails userDetails) {
        userService.logout(userDetails.user.emailAddress)
        request.logout()
    }
}
