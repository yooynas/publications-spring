package com.carlospaelinck.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSetter
import org.hibernate.annotations.GenericGenerator

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Transient
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotNull

/**
 * Created by carlos13 on 1/25/16.
 */
@Entity
@Table(name="\"user\"")
class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    def String id

    @NotNull
    @Column(unique=true)
    def String emailAddress

    @NotNull
    def String passwordHash

    @Transient
    def String password

    @NotNull
    def Boolean temporary

    @OneToMany(targetEntity = Document.class, mappedBy = 'user')
    def List<Document> documents
}
