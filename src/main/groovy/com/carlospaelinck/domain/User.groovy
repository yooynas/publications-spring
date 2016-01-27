package com.carlospaelinck.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
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

@Entity
@Table(name="\"user\"")
class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id

    @NotNull
    @Column(unique=true)
    String emailAddress

    @NotNull
    @JsonIgnore
    String passwordHash

    @Transient
    String password

    @NotNull
    Boolean temporary

    @OneToMany(targetEntity = Document.class, mappedBy = 'user')
    @JsonIgnore
    List<Document> documents

    @JsonIgnore
    String getPassword() {
        return password
    }

    @JsonProperty
    void setPassword(String password) {
        this.password = password
    }
}
