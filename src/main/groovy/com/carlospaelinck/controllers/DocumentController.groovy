package com.carlospaelinck.controllers


import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.DocumentRepository
import com.carlospaelinck.security.PublicationsUserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.inject.Inject

/**
 * Created by carlos13 on 1/25/16.
 */

@RestController
@RequestMapping(value = '/documents')
class DocumentController {
    @Inject
    DocumentRepository documentRepository

    @RequestMapping(method = RequestMethod.GET)
    List<Document> list(@AuthenticationPrincipal PublicationsUserDetails userDetails) {
        return documentRepository.findAllByUser(userDetails.user)
    }

    @RequestMapping(method = RequestMethod.POST)
    Document create(@AuthenticationPrincipal PublicationsUserDetails userDetails, @RequestBody Document document) {
        document.user = userDetails.user
        return documentRepository.save(document)
    }
}
