package no.sanderpriv.vinvenn.di

import no.sanderpriv.vinvenn.api.VinVennApi
import no.sanderpriv.vinvenn.repository.VinVennRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object VinVennDI {

    val apiModule = module {
        single<VinVennApi> {
            Retrofit.Builder()
                .baseUrl("https://vinvenn.no/api/")
                .client(
                    OkHttpClient().newBuilder()
                        .readTimeout(1, TimeUnit.MINUTES)
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .addInterceptor(
                            HttpLoggingInterceptor { Timber.d("okhttp: $it") }.apply {
                                setLevel(HttpLoggingInterceptor.Level.BODY)
                            }
                        ).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(VinVennApi::class.java)
        }
    }

    val repositoryModule = module {
        singleOf(::VinVennRepository)
    }
}

