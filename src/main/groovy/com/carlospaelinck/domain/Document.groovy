package com.carlospaelinck.domain

import org.hibernate.annotations.GenericGenerator

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Created by carlos13 on 1/25/16.
 */
@Entity
class Document {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    def String id

    @ManyToOne(targetEntity = User.class)
    def User user

    @NotNull
    def String name

    @NotNull
    def Float width

    @NotNull
    def Float height
}
