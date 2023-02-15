package com.example.majika.helper

import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionCheck {
    private const val TAG = "PermissionRequest"
    private const val RECORD_REQUEST_CODE = 101

    fun setupPermission(activity: AppCompatActivity, permission: String) {
        // set up permission
        val permissionBool = ContextCompat.checkSelfPermission(activity, permission)

        if (permissionBool != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission $permission denied")
            ActivityCompat.requestPermissions(activity, arrayOf(permission), RECORD_REQUEST_CODE)
        }
    }

    fun handlePermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when(requestCode){
            RECORD_REQUEST_CODE  -> {
                val perm = permissions[0]
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Log.i(TAG,"Permissions $perm has been denied by user")
                else
                    Log.i(TAG,"Permission $perm has been granted by user")
            }
        }
    }
}