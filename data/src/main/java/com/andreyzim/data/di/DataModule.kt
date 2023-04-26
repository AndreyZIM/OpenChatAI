package com.andreyzim.data.di

import android.content.Context
import android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback
import androidx.room.Room
import com.andreyzim.data.BaseOpenChatRepository
import com.andreyzim.data.Constants
import com.andreyzim.data.HandleDataError
import com.andreyzim.data.HandleDataRequest
import com.andreyzim.data.MessageData
import com.andreyzim.data.MessageToDomainMapper
import com.andreyzim.data.cache.*
import com.andreyzim.data.cloud.MessageCloudDataSource
import com.andreyzim.data.cloud.OpenChatService
import com.andreyzim.domain.HandleError
import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.OpenChatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCacheDataSource(
        dao: MessageDao,
        dataToCache: DataToCacheMapper
    ): MessageCacheDataSource = MessageCacheDataSource(dao, dataToCache)

    @Provides
    fun provideToDomainMapper(): MessageData.Mapper<MessageDomain> = MessageToDomainMapper()

    @Provides
    fun provideToCacheMapper(): MessageData.Mapper<MessageCache> = DataToCacheMapper()

    @Provides
    @Singleton
    fun provideRepository(
        cacheDataSource: MessageCacheDataSource,
        cloudDataSource: MessageCloudDataSource,
        mapper: MessageData.Mapper<MessageDomain>,
        handleDataRequest: HandleDataRequest
    ): OpenChatRepository = BaseOpenChatRepository(cacheDataSource, cloudDataSource, mapper, handleDataRequest)

    @Provides
    fun provideHandleDataRequest(
        handleError: HandleError<Exception>
    ) : HandleDataRequest = HandleDataRequest.Base(handleError)

    @Provides
    fun provideHandleError() : HandleError<Exception> = HandleDataError()

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MessageDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MessageDatabase::class.java,
            "messages_database"
        )
            .fallbackToDestructiveMigration()
            .build()



    @Provides
    fun provideDao(database: MessageDatabase): MessageDao = database.messageDao()

    @Provides
    @Singleton
    fun provideCloudDataSource(service: OpenChatService): MessageCloudDataSource =
        MessageCloudDataSource(service)

    @Provides
    @Singleton
    fun provideBaseUrl() : String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideClient(interceptor: HttpLoggingInterceptor) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideCameraService(retrofit: Retrofit) : OpenChatService = retrofit.create(OpenChatService::class.java)
}
