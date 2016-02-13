package com.carlospaelinck.services

import com.carlospaelinck.domain.User
import com.carlospaelinck.security.PublicationsUserDetails

interface UserService {

    User current(String emailAddress)

    User create(User user)

    User update(PublicationsUserDetails userDetails, User user)

    User login(User user)

    def logout(PublicationsUserDetails userDetails)
}