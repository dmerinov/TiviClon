package com.example.tiviclon.model.application

import java.io.Serializable


data class AppUser(
    val name: String = "",
    val password: String = ""
) : Serializable