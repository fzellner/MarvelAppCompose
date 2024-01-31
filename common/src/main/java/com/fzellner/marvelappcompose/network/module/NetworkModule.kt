package com.fzellner.marvelappcompose.network.module

import android.util.Log
import com.fzellner.marvelappcompose.network.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val NETWORK_LAYER_TAG = "NetworkLayer"
    private val APPLICATION_LAYER_TAG = "ApplicationLayer"
    private val API_KEY = "apikey"
    private val TIME_STAMP = "ts"
    private val HASH = "hash"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideOkhttp(): OkHttpClient {
        val networkLogging = HttpLoggingInterceptor { message ->
            Log.d(NETWORK_LAYER_TAG, message)
        }
        val appLogging = HttpLoggingInterceptor { message ->
            Log.d(APPLICATION_LAYER_TAG, message)
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            connectTimeout(120, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
            writeTimeout(120, TimeUnit.SECONDS)
            addInterceptor(networkLogging)
            addInterceptor(appLogging)
            addInterceptor(loggingInterceptor)
            addInterceptor { interceptor ->
                val request = interceptor.request()
                val url = request.url.newBuilder()
                    .addQueryParameter(
                        name = API_KEY,
                        value = BuildConfig.apiPublicKey
                    ).addQueryParameter(
                        name = TIME_STAMP,
                        value = getTimeStamp()
                    ).addQueryParameter(
                        name = HASH,
                        value = generateMD5Hash()
                    ).build()

                val newRequest = request.newBuilder()
                    .url(url)
                    .method(request.method, request.body)

                interceptor.proceed(newRequest.build())
            }
        }.build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(okHttpClient)
            baseUrl(BuildConfig.baseUrl)
        }.build()
    }

    private fun getTimeStamp(): String {
        val calendar = Calendar.getInstance()
        return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(calendar.time)
    }

    private fun generateMD5Hash(): String {
        val input = getTimeStamp() + BuildConfig.apiPrivateKey + BuildConfig.apiPublicKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16)
            .padStart(32, '0')
    }

}