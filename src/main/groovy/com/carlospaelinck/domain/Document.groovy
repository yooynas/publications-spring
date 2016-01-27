package com.carlospaelinck.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
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
    String id

    @ManyToOne(targetEntity = User.class, optional = false)
    @JsonIgnore
    @NotNull
    User user

    @NotNull
    String name

    @NotNull
    Float width

    @NotNull
    Float height

    @OneToMany(targetEntity = Shape.class, cascade = CascadeType.ALL)
    List<Shape> shapes
}
