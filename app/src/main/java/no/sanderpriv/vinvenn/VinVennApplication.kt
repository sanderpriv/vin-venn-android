package no.sanderpriv.vinvenn

import android.app.Application
import no.sanderpriv.vinvenn.di.VinVennDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class VinVennApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@VinVennApplication)
            modules(VinVennDI.getAppModules())
        }

        Timber.plant(Timber.DebugTree())
    }
}
