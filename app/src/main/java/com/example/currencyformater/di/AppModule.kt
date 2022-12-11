package com.example.currencyformater.di

import android.app.Application
import androidx.room.Room
import com.example.currencyformater.common.Constants.API_KEY
import com.example.currencyformater.common.Constants.APP_DATABASE
import com.example.currencyformater.common.Constants.BASE_URL
import com.example.currencyformater.data.local.GeneralDatabase
import com.example.currencyformater.data.remote.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    //this will satisfy provideRetrofit
    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                    .addHeader("apiKey", API_KEY)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    //this is going to return gsonConverterFactory to provideRetrofit
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    //like every retrofit builder in order to get our data
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    //this will give the api instance in our remoteDataSource
    //singleton means we re going to have only one instance of this
    //we re using application scope for this API
    //Provides is if instances must be created with the builder pattern.
    //or if you dont own the class because it comes from external library (Retrofit, Room etc)
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): GeneralDatabase {
        return Room.databaseBuilder(
            app,
            GeneralDatabase::class.java,
            APP_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideBalanceDao(database: GeneralDatabase) = database.balancesDao

    @Provides
    @Singleton
    fun provideTransactionDao(database: GeneralDatabase) = database.transactionsDao

}