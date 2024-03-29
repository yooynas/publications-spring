package com.carlospaelinck.security

import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

import javax.inject.Inject
import javax.inject.Named

@Named
class PublicationsUserDetailsService implements UserDetailsService {

    @Inject
    UserRepository userRepository

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        def user = userRepository.findOneByEmailAddress(username)

        if (user == null) {
            throw new UsernameNotFoundException('User with that email not found')
        }

        return new PublicationsUserDetails(user)
    }
}
