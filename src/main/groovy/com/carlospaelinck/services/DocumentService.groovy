package com.carlospaelinck.services

import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User

interface DocumentService {

    Document get(String id)

    List<Document> findAllByUser(User user)

    Document create(Document document)

    Document update(Document document)

    Void delete(String id)

    File pdf(String documentId)
}
