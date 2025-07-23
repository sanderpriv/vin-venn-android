package no.sanderpriv.vinvenn.di

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import kotlin.test.Test


class VinVennDITest: KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAppModule() {
        VinVennDI.getAppModules().verify()
    }
}
