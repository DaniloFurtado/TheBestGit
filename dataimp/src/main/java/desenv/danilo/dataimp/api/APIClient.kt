package desenv.danilo.dataimp.api

import desenv.danilo.data.util.Constantes
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import javax.net.ssl.SSLSocketFactory

object APIClient {
    private var retrofit: Retrofit? = null

    enum class LogLevel {
        LOG_NOT_NEEDED,
        LOG_REQ_RES,
        LOG_REQ_RES_BODY_HEADERS,
        LOG_REQ_RES_HEADERS_ONLY
    }

    /**
     * Returns Retrofit builder to create
     * @param logLevel - to print the log of Request-Response
     * @return retrofit
     */
    fun getClient(logLevel: LogLevel): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        when(logLevel) {
            LogLevel.LOG_NOT_NEEDED ->
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            LogLevel.LOG_REQ_RES ->
                interceptor.level = HttpLoggingInterceptor.Level.BASIC
            LogLevel.LOG_REQ_RES_BODY_HEADERS ->
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            LogLevel.LOG_REQ_RES_HEADERS_ONLY ->
                interceptor.level = HttpLoggingInterceptor.Level.HEADERS

        }

        val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()


        if(null == retrofit) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }

        return retrofit!!
    }

    fun getAPIService(logLevel: LogLevel = LogLevel.LOG_REQ_RES_BODY_HEADERS) = getClient(logLevel)
}