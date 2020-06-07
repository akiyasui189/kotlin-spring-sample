package io.spring.start.kotlin.sample.repositories.mappers

import io.spring.start.kotlin.sample.entities.tables.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper {

    @Select("""
        SELECT 
            id as userId,
            username as account,
            password,
            status,
            email,
            email_verified_at as emailVedifiedAt,
            created_at as createdAt,
            created_by as createdBy,
            created_at as createdAt,
            created_by as createdBy, 
            version,
            deleted
        FROM users
        WHERE
            deleted = 0
    """)
    fun findAll(): List<User>

    @Select("""
        SELECT 
            id as userId,
            username as account,
            password,
            status,
            email,
            email_verified_at as emailVedifiedAt,
            created_at as createdAt,
            created_by as createdBy,
            created_at as createdAt,
            created_by as createdBy, 
            version,
            deleted
        FROM users
        WHERE
            (
                username = #{username}
                OR
                email = #{email}
            )
            AND
            deleted = 0
    """)
    fun findByUsernameOrEmail(username:String, email: String): List<User>

}