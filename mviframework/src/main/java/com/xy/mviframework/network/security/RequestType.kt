package com.xy.mviframework.network.security

enum class RequestType(var requestType:String) {
    DEFAULT("{DATA-DEFAULT}"),
    SINGLE("{DATA-SINGLE}"),
    TWO_WAY("{DATA-TWO-WAY}")
}