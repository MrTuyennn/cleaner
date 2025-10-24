package com.inetkr.cleaner.utils.perm

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

fun mediaPermissionsForSdk(): Array<String> =
    if (Build.VERSION.SDK_INT >= 33) arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_AUDIO
    ) else arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

@Composable
fun RequestMediaPermission(
    onGranted: () -> Unit,
    onDenied: (permanently: Boolean) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val perms = remember { mediaPermissionsForSdk() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result.values.all { it }
        if (granted) onGranted()
        else {
            val permanentlyDenied = perms.any { p ->
                ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, p)
            }
            onDenied(permanentlyDenied)
        }
    }

    LaunchedEffect(Unit) {
        val hasAll = perms.all { p ->
            ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED
        }
        if (hasAll) onGranted() else launcher.launch(perms)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, perms) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val hasAll = perms.all { p ->
                    ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED
                }
                if (hasAll) onGranted()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}