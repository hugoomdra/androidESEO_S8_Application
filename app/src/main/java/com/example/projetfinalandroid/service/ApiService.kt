package com.example.projetfinalandroid.service

import androidx.viewbinding.BuildConfig
import com.example.projetfinalandroid.data.models.Data
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*
import java.util.concurrent.TimeUnit

interface ApiService {

    // Début définition des différentes API disponible sur votre serveur
    @GET("/todos/1")
    suspend fun readData(@Query("device_token") device_token: String): Array<Data>
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
            System.out.println("test avant build")


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

            System.out.println("test après build")
            System.out.println(okHttpClient)


            return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com") // Mieux -> BuildConfig.URI_REMOTE_SERVER, oui oui écrire en dur un lien est une TRÈS MAUVAISE IDÉE !
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}