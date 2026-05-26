package vn.vtv.vtvgotv.core.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Dedicated Authorization & Token Refresh Interceptor.
 * Focuses strictly on prepending dynamic auth token headers and catching token errors for refreshing.
 */
class AuthInterceptor(
    private val onRequest: ((Request.Builder) -> Unit)? = null,
    private val onError: ((Response) -> Unit)? = null
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // 1. Trigger onRequest callback before proceeding request
        onRequest?.invoke(requestBuilder)

        val request = requestBuilder.build()
        val response = chain.proceed(request)

        // 2. Trigger onError callback if request fails (e.g. 401 Unauthorized for token refresh flow)
        if (!response.isSuccessful) {
            onError?.invoke(response)
        }

        return response
    }
}
