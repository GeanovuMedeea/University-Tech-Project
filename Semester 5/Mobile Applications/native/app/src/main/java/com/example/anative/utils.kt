import android.util.Log

fun logd(message: String? = "missing message", cause: Throwable? = null) {
    Log.d("Native", message, cause)
}

fun loge(message: String? = "missing message", cause: Throwable? = null) {
    Log.e("Native", message, cause)
}

class MyException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}