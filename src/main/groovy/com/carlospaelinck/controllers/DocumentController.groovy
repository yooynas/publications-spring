package com.carlospaelinck.controllers


import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.DocumentRepository
import com.carlospaelinck.security.PublicationsUserDetails
import com.carlospaelinck.services.DocumentService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.inject.Inject

@RestController
@RequestMapping(value = '/documents')
class DocumentController {
    @Inject
    DocumentService documentService

    @RequestMapping(method = RequestMethod.GET)
    List<Document> list(@AuthenticationPrincipal PublicationsUserDetails userDetails) {
        return documentService.findAllByUser(userDetails.user)
    }

    @RequestMapping(method = RequestMethod.POST)
    Document create(@AuthenticationPrincipal PublicationsUserDetails userDetails, @RequestBody Document document) {
        document.user = userDetails.user
        return documentService.create(document)
    }

    @RequestMapping(value = '/{documentId}', method = RequestMethod.PUT)
    Document update(@AuthenticationPrincipal PublicationsUserDetails userDetails, @PathVariable('documentId') String documentId, @RequestBody Document document) {
        document.id = documentId
        document.user = userDetails.user
        return documentService.update(document)
    }
}
