package com.carlospaelinck.services

import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.DocumentRepository
import org.springframework.stereotype.Service

import javax.inject.Inject
import javax.transaction.Transactional

@Service
@Transactional
class DocumentServiceImpl implements DocumentService {

    @Inject
    DocumentRepository documentRepository;

    @Override
    Document findAllByUser(User user) {
        documentRepository.findAllByUser(user);
    }

    @Override
    Document create(Document document) {
        return documentRepository.save(document)
    }

    @Override
    Document update(Document document) {
        return documentRepository.save(document)
    }
}
