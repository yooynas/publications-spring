package com.carlospaelinck.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity
class Shape {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id

    @NotNull
    String type

    @NotNull
    Float width

    @NotNull
    Float height

    @NotNull
    Float x

    @NotNull
    Float y

    Float r
    Float angle
    Float strokeWidth

    String fill
    String color
    String stroke
    Float fillOpacity
    Float strokeOpacity

    String text
    String fontFamily
    Integer fontSize
    String fontWeight
    String fontStyle
    String textAlign
}
