package com.carlospaelinck.services

import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.UserRepository
import com.carlospaelinck.security.PublicationsUserDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

import javax.inject.Inject
import javax.transaction.Transactional

@Service
@Transactional
class UserServiceImpl implements UserService {
    @Inject
    UserRepository userRepository

    @Inject
    DocumentService documentService

    @Inject
    AuthenticationManager authenticationManager

    @Override
    User current(String emailAddress) {
        return userRepository.findOneByEmailAddress(emailAddress)
    }

    @Override
    User create(User user) {
        user.setPasswordHash(new BCryptPasswordEncoder().encode(user.getPassword()))
        return userRepository.save(user)
    }

    @Override
    User update(PublicationsUserDetails userDetails, User user) {
        userDetails.user.setPasswordHash(new BCryptPasswordEncoder().encode(user.getPassword()))
        userDetails.user.emailAddress = user.emailAddress
        userDetails.user.temporary = user.temporary
        return userRepository.save(userDetails.user)
    }

    @Override
    User login(User user) {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(user.emailAddress, user.password)

        // Authenticate the user
        def authentication = authenticationManager.authenticate(authRequest)
        SecurityContext securityContext = SecurityContextHolder.getContext()
        securityContext.setAuthentication(authentication)
        return userRepository.findOneByEmailAddress(user.emailAddress)
    }

    @Override
    def logout(PublicationsUserDetails userDetails) {
        def user = userRepository.findOneByEmailAddress(userDetails.user.emailAddress)

        if (user.temporary) {
            user.documents.forEach({doc -> documentService.delete(doc.id)})
            userRepository.delete(user)
        }

        SecurityContextHolder.getContext().setAuthentication(null)
    }
}
