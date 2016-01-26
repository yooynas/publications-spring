package com.carlospaelinck.controllers

import com.carlospaelinck.domain.Document
import com.carlospaelinck.repositories.DocumentRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.inject.Inject

/**
 * Created by carlos13 on 1/25/16.
 */

@RestController
@RequestMapping(value = '/documents')
class DocumentController {
    @Inject
    def DocumentRepository documentRepository

    def List<Document> list() {
//        return documentRepository.find
    }
}
