package kz.islam.sanayatov.test.service

import kz.islam.sanayatov.test.data.User
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

interface UserService {
    fun getCurrentUser(): User
}

@Service
class UserServiceImpl: UserService {

    override fun getCurrentUser(): User {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        if (authentication is AnonymousAuthenticationToken) {
            throw IllegalArgumentException()
        }
        return User(authentication.name)
    }
}