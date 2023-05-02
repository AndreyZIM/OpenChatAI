package com.andreyzim.domain

abstract class DomainException : IllegalStateException()

class ServiceUnavailableException : DomainException()

class RequestTimeoutException : DomainException()

class NotEnoughTokesException : DomainException()

class WrongApiKeyException : DomainException()