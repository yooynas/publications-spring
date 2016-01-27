package com.carlospaelinck.security;

import com.carlospaelinck.domain.User;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class PublicationsUserDetails extends org.springframework.security.core.userdetails.User {

    User user

    public PublicationsUserDetails(User user) {
        super(user.emailAddress, user.passwordHash, AuthorityUtils.createAuthorityList())
        this.user = user
    }
}
