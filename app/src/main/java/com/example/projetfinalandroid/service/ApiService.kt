package com.example.projetfinalandroid.service

import androidx.viewbinding.BuildConfig
import com.example.projetfinalandroid.data.models.Data
import com.example.projetfinalandroid.data.models.Device
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.concurrent.TimeUnit

interface ApiService {

    // Début définition des différentes API disponible sur votre serveur
    @GET("/public/api/data")
    suspend fun readData(@Query("device_token") device_token: String): Array<Data>

    @FormUrlEncoded
    @POST("/public/api/data")
    suspend fun refreshData(
        @Field("device_token") device_token: String,
        @Field("luminosity") luminosity: String,
        @Field("battery_level") battery_level: String,
        @Field("pressure") pressure: String,
        @Field("temperature") temperature: String,
        @Field("position") position: String,
    ): Data

    @FormUrlEncoded
    @POST("/public/api/devices")
    suspend fun createToken(
        @Field("nom") nom: String,
        @Field("device_token") device_token: String,
    ): Device


//
//    @POST("/status")
//    suspend fun writeStatus(@Body status: SampleObject): Array<SampleObject>
    // Fin de la définition

    // Équivalenent en kotlin d'élément « static »
    companion object {
        /**
         * Création d'un singleton pour la simplicité, mais normalement nous utilisons plutôt un
         * injecteur de dépendances.
         */
        val instance = build()

        /**
         * Création de l'objet qui nous permettra de faire les appels d'API
         */
        private fun build(): ApiService {
            val gson = GsonBuilder().create() // JSON deserializer/serializer

            // Create the OkHttp Instance
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()
                    chain.proceed(request)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl("https://hidden-chamber-01030.herokuapp.com/") // Mieux -> BuildConfig.URI_REMOTE_SERVER, oui oui écrire en dur un lien est une TRÈS MAUVAISE IDÉE !
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}