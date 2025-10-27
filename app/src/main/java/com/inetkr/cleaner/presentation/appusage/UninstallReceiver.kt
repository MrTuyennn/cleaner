package com.inetkr.cleaner.presentation.appusage

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UninstallReceiver: BroadcastReceiver() {

    companion object {
        var onAppUninstalledListener: ((String) -> Unit)? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_PACKAGE_REMOVED -> {
                val packageName = intent.data?.schemeSpecificPart
                println("=== App uninstalled: $packageName ===")
                onAppUninstalledListener?.invoke(packageName.toString())
            }
            Intent.ACTION_PACKAGE_FULLY_REMOVED -> {
                val packageName = intent.data?.schemeSpecificPart
                println("=== App fully uninstalled: $packageName ===")
              //  onAppUninstalledListener?.invoke(packageName.toString())
            }
            Intent.ACTION_PACKAGE_REPLACED -> {
                val packageName = intent.data?.schemeSpecificPart
                println("=== App replaced: $packageName ===")
             //   onAppUninstalledListener?.invoke(packageName.toString())
            }
            else -> {
                println("UninstallReceiver Unknown action: ${intent?.action}")
            }
        }
    }
}