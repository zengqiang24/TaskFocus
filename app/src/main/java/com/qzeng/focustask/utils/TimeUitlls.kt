package com.qzeng.focustask.utils

private fun getString(t: Long): String {
    var m = ""
    m = if (t > 0) {
        if (t < 10) {
            "0$t"
        } else {
            t.toString() + ""
        }
    } else {
        "00"
    }
    return m
}


fun formatDateToString(t: Long): String {
    return when {
        t < 60000 -> {
            "${(t % 60000) / 1000}"
        }
        t in 60000..3599999 -> {
            getString(t % 3600000 / 60000) + ":" + getString(t % 60000 / 1000)
        }
        else -> {
            getString(t / 3600000) + ":" + getString(t % 3600000 / 60000) + ":" + getString(t % 60000 / 1000)
        }
    }
}
