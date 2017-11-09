package com.example.gocantar.connectingthings.data

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.example.gocantar.connectingthings.AppController

/**
 * Created by gocantar on 13/10/17.
 */
object PermissionsService {

    val REQUEST_CODE: Int  = 4300

    fun requiredPermissions(): Array<String> {
        val listOfPermissions: MutableList<String> = mutableListOf()
        if (!isPermissionsGranted()) {
            listOfPermissions.addAll(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
        }
        return listOfPermissions.toTypedArray()
    }

    private fun isPermissionsGranted(): Boolean{
        return ActivityCompat.checkSelfPermission(AppController.instance.baseContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppController.instance.baseContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

}