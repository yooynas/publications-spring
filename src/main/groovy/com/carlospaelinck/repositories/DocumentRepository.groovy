package com.carlospaelinck.repositories

import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentRepository extends PagingAndSortingRepository<Document, String> {
    List<Document> findAllByUser(User user)
}
