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
        val userId: Long = 0,
        @Column(name = "username")
        val account: String,
        @Column(name = "password")
        val password: String,
        @Column(name = "status")
        val status: String,
        @Column(name = "email")
        val email: String,
        @Column(name = "email_verified_at")
        val emailVerifiedAt: LocalDateTime?,
        @Column(name = "created_at")
        val createdAt: LocalDateTime,
        @Column(name = "created_by")
        val createdBy: String,
        @Column(name = "updated_at")
        val updatedAt: LocalDateTime,
        @Column(name = "updated_by")
        val updatedBy: String,
        @Column(name = "version")
        val version: Int,
        @Column(name = "deleted")
        val deleted: Boolean
)