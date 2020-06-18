package com.marcoscouto.pontointeligente.pontointeligente.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordUtilsTest {

    private val PASS = "1234"
    private val bCryptEncoder = BCryptPasswordEncoder()

    @Test
    fun shouldGenerateEncodePassword(){
        val hash =  PasswordUtils().generateBCrypt(PASS)
        Assertions.assertTrue(bCryptEncoder.matches(PASS, hash))
    }
}