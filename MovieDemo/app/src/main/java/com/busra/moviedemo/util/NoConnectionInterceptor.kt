package com.busra.moviedemo.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoConnectionInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn()) {
            return Response.Builder()
                       .request(chain.request())
                       .protocol(Protocol.HTTP_1_1)
                       .code(400)
                       .message("client config invalid")
                       .body("client config invalid".toResponseBody(null))
                       .build()
        } else if(!isInternetAvailable()) {
            //throw NoInternetException()
            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(400)
                .message("client config invalid 2")
                .body("client config invalid 2".toResponseBody(null))
                .build()
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun isConnectionOn(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val connection = connectivityManager.getNetworkCapabilities(network)
            return connection != null && (
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
            }
            return false
        }
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockaddr, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }

    }

    class NoConnectivityException : IOException() {
        override val message: String
            get() = "No network available, please check your WiFi or Data connection"
    }

    class NoInternetException() : IOException() {
        override val message: String
            get() = "No internet available, please check your connected WIFi or Data"
    }
}