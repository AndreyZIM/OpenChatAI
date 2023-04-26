package com.andreyzim.domain

interface HandleError<T> {

    fun handle(e: Exception): T

    // TODO implement domain error handling
}