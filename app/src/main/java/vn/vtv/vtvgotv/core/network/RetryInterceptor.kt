package vn.vtv.vtvgotv.core.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Dedicated Retry Interceptor.
 * Focuses strictly on connection recovery and server error retry handling up to 3 times.
 */
class RetryInterceptor : Interceptor {
    companion object {
        private const val TAG = "VtvNetworkRetry"
        private const val MAX_RETRIES = 3
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        var exception: Exception? = null
        var tryCount = 0
        var isSuccessful = false

        while (!isSuccessful && tryCount < MAX_RETRIES) {
            tryCount++
            try {
                if (tryCount > 1) {
                    Log.w(TAG, "🔄 Retrying network request... (Attempt $tryCount/$MAX_RETRIES)")
                }
                response = chain.proceed(request)

                // Stop retrying on HTTP success or standard client errors (4xx)
                if (response.isSuccessful || response.code in 400..499) {
                    isSuccessful = true
                } else {
                    // It is a server error (5xx)
                    if (tryCount == MAX_RETRIES) {
                        isSuccessful = true
                    } else {
                        response.close() // Close connection before retry to avoid resource leak
                    }
                }
            } catch (e: Exception) {
                exception = e
                Log.e(TAG, "❌ Connection failed: ${e.message} (Attempt $tryCount/$MAX_RETRIES)")
                if (tryCount == MAX_RETRIES) {
                    throw e
                }
            }
        }

        return response ?: throw exception ?: IOException("Unknown network exception occurred")
    }
}
