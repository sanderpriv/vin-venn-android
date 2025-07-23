package no.sanderpriv.vinvenn.di

import no.sanderpriv.vinvenn.api.VinVennApi
import no.sanderpriv.vinvenn.repository.VinVennRepository
import no.sanderpriv.vinvenn.ui.meals.MealsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object VinVennDI {

    private val apiModule = module {
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

    private val repositoryModule = module {
        singleOf(::VinVennRepository)
    }

    private val viewModelModule = module {
        viewModelOf(::MealsViewModel)
    }

    fun getAppModules() = module {
        includes(apiModule, repositoryModule, viewModelModule)
    }
}
