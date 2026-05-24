package com.popflix.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.popflix.BuildConfig
import com.popflix.data.local.PopflixDatabase
import com.popflix.data.remote.api.*
import com.popflix.data.source.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "popflix_settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/") // TODO: Replace with your actual TMDB base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideMegaCloudApiService(retrofit: Retrofit): MegaCloudApiService {
        // TODO: Replace with actual Mega Cloud base URL
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MEGA_CLOUD_BASE_URL)
            .client(retrofit.client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MegaCloudApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideWootlyApiService(retrofit: Retrofit): WootlyApiService {
        // TODO: Replace with actual Wootly base URL
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WOOTLY_BASE_URL)
            .client(retrofit.client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WootlyApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideVidSrcApiService(retrofit: Retrofit): VidSrcApiService {
        // TODO: Replace with actual VidSrc base URL
        return Retrofit.Builder()
            .baseUrl(BuildConfig.VIDSRC_BASE_URL)
            .client(retrofit.client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VidSrcApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideMfcApiService(retrofit: Retrofit): MfcApiService {
        // TODO: Replace with actual MFC base URL
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MFC_BASE_URL)
            .client(retrofit.client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MfcApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PopflixDatabase {
        return Room.databaseBuilder(
            context,
            PopflixDatabase::class.java,
            PopflixDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideWatchlistDao(database: PopflixDatabase) = database.watchlistDao()
    
    @Provides
    @Singleton
    fun provideWatchHistoryDao(database: PopflixDatabase) = database.watchHistoryDao()
    
    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: PopflixDatabase) = database.searchHistoryDao()
    
    @Provides
    @Singleton
    fun provideMovieCacheDao(database: PopflixDatabase) = database.movieCacheDao()
    
    @Provides
    @Singleton
    fun provideTvShowCacheDao(database: PopflixDatabase) = database.tvShowCacheDao()
    
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
    
    @Provides
    @Singleton
    fun provideVideoSourceManager(
        megaCloudApi: MegaCloudApiService,
        wootlyApi: WootlyApiService,
        vidSrcApi: VidSrcApiService,
        mfcApi: MfcApiService
    ): VideoSourceManager {
        val sources = listOf(
            MegaCloudSource(megaCloudApi),
            WootlySource(wootlyApi),
            VidSrcSource(vidSrcApi),
            MfcSource(mfcApi)
        )
        return VideoSourceManager(sources)
    }
}
