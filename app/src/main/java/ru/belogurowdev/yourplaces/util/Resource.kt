package ru.belogurowdev.yourplaces.util

data class Resource<out T>(val status: WebStatus, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(WebStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(WebStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(WebStatus.LOADING, data, null)
        }
    }
}