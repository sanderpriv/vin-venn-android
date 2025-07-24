package no.sanderpriv.vinvenn.repository

import java.util.Date

fun Date.isExpired() = this < Date()
