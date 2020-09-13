package io.spring.start.kotlin.sample.entities.tables

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var userId: Long?,
        @Column(name = "username")
        var account: String,
        @Column(name = "password")
        var password: String,
        @Column(name = "status")
        var status: String,
        @Column(name = "email")
        var email: String,
        @Column(name = "email_verified_at")
        var emailVerifiedAt: LocalDateTime?,
        @Column(name = "created_at")
        var createdAt: LocalDateTime,
        @Column(name = "created_by")
        var createdBy: String,
        @Column(name = "updated_at")
        var updatedAt: LocalDateTime,
        @Column(name = "updated_by")
        var updatedBy: String,
        @Column(name = "version")
        var version: Int,
        @Column(name = "deleted")
        var deleted: Boolean
)