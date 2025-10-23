package com.inetkr.cleaner.domain.entity

data class AppConfig(
    val versionNumber: String = "",
    var buildNumber: String = "",
    var displayName: String = "",
    var bundleName: String = "",
    var uuid: String = "",
    var locales: String = "",
    var alphaCode: String = "",
    var timeZone: String = "",
    var isLowRamDevice: Boolean = false,
    var physicalRamSize: Int = 0,
    var availableRamSize: Int = 0,

)