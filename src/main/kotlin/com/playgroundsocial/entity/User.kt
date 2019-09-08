package com.playgroundsocial.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.Email


@Entity
@Table(name = "users")
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val username: String,

    @Email
    @Column(nullable = false)
    val email: String,

    @Column
    val imageUrl: String,

    @JsonIgnore
    val password: String
)
