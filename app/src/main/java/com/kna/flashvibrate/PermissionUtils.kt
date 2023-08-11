package com.kna.flashvibrate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtils {

    fun checkReadPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun checkMicroPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.RECORD_AUDIO)
    }


    private fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(activity: Activity, permission: String) {
        var requestCode = when (permission) {
            Manifest.permission.RECORD_AUDIO -> {
                REQUEST_MICRO_PERMISSION_CODE
            }

            Manifest.permission.READ_EXTERNAL_STORAGE -> {
                REQUEST_READ_PERMISSION_CODE
            }

            else -> {
                REQUEST_PERMISSION_CODE
            }
        }
        activity.requestPermissions(
            arrayOf(permission),
            requestCode
        )
    }

    fun requestReadPermission(activity: Activity) {
        requestPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun requestMicroPermission(activity: Activity) {
        requestPermission(activity, Manifest.permission.RECORD_AUDIO)
    }
}
