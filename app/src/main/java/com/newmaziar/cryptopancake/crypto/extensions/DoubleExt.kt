package com.newmaziar.cryptopancake.crypto.extensions


fun Double?.orDefault(default: Double = -1.0): Double = this ?: default