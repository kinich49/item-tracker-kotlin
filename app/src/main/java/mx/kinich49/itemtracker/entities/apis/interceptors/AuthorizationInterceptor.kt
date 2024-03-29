package mx.kinich49.itemtracker.entities.apis.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val authToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", authToken)
            .build()

        return chain.proceed(authenticatedRequest)
    }

}