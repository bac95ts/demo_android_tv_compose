package vn.vtv.vtvgotv.core.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

/**
 * Dedicated Logging Interceptor.
 * Focuses strictly on printing beautiful log formatting and generating copy-pasteable cURL commands.
 */
class LoggingInterceptor : Interceptor {
    companion object {
        private const val TAG = "VtvNetwork"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logRequest(request)

        val startTime = System.nanoTime()
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            Log.e(TAG, "❌ HTTP Request failed: ${e.message}")
            throw e
        }
        val durationMs = (System.nanoTime() - startTime) / 1e6

        logResponse(response, durationMs)
        return response
    }

    private fun logRequest(request: Request) {
        val curl = generateCurlCommand(request)
        Log.d(TAG, "===================== 🚀 REQUEST =====================")
        Log.d(TAG, "URL    : ${request.method} ${request.url}")
        
        if (request.headers.size > 0) {
            Log.d(TAG, "Headers:")
            for (name in request.headers.names()) {
                Log.d(TAG, "  - $name: ${request.header(name)}")
            }
        }
        
        val requestBody = request.body
        if (requestBody != null) {
            try {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val charset = requestBody.contentType()?.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
                Log.d(TAG, "Body   : ${buffer.readString(charset)}")
            } catch (e: Exception) {
                Log.d(TAG, "Body   : [Could not read request body]")
            }
        }
        
        Log.d(TAG, "--------------------- 📋 cURL COMMAND ---------------------")
        Log.d(TAG, curl)
        Log.d(TAG, "=======================================================")
    }

    private fun logResponse(response: Response, durationMs: Double) {
        Log.d(TAG, "===================== 📥 RESPONSE =====================")
        Log.d(TAG, "URL    : ${response.request.method} ${response.request.url}")
        Log.d(TAG, "Code   : ${response.code} ${response.message} (took ${"%.1f".format(durationMs)} ms)")
        
        val responseBody = response.body
        if (responseBody != null) {
            try {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire response body
                val buffer = source.buffer
                val charset = responseBody.contentType()?.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
                val responseBodyString = buffer.clone().readString(charset)
                Log.d(TAG, "Body   : $responseBodyString")
            } catch (e: Exception) {
                Log.d(TAG, "Body   : [Could not read response body]")
            }
        }
        Log.d(TAG, "=======================================================")
    }

    private fun generateCurlCommand(request: Request): String {
        val curlCmd = StringBuilder("curl -X ${request.method}")
        for (name in request.headers.names()) {
            curlCmd.append(" -H \"$name: ${request.header(name)}\"")
        }
        val requestBody = request.body
        if (requestBody != null) {
            try {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val charset = requestBody.contentType()?.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
                val bodyString = buffer.readString(charset).replace("\"", "\\\"")
                curlCmd.append(" -d \"$bodyString\"")
            } catch (e: Exception) {
                // Ignore body formatting errors
            }
        }
        curlCmd.append(" \"${request.url}\"")
        return curlCmd.toString()
    }
}
