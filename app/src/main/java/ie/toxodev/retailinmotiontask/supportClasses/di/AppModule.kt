package ie.toxodev.retailinmotiontask.supportClasses.di

import android.content.Context
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.toxodev.retailinmotiontask.supportClasses.forecastAPI.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun appContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun providesOkHttpInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    fun forecastAPI(okHttpClient: OkHttpClient): API {
        return Retrofit.Builder()
            .addConverterFactory(TikXmlConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("http://luasforecasts.rpa.ie/")
            .build()
            .create(API::class.java)
    }
}