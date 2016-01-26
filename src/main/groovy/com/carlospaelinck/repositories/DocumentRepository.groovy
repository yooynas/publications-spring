package com.carlospaelinck.repositories

import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 * Created by carlos13 on 1/25/16.
 */

@Repository
interface DocumentRepository extends PagingAndSortingRepository<Document, String> {
    def List<Document> findAllByUser(User user)
}
