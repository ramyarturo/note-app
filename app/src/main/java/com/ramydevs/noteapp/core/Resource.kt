package com.ramydevs.noteapp.core

sealed class Resource<T> {
    data class Success<T>(val data:T? = null) : Resource<T>()
    data class Failure(val message:String?) : Resource<Nothing>()
}
