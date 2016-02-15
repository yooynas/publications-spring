package com.carlospaelinck.services

import com.carlospaelinck.domain.User

interface UserService {

    User get(String emailAddress)

    User create(User user)

    User update(User user)

    User login(User user)

    def logout(String emailAddress)
}