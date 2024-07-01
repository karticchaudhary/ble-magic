package com.kartic.blemagic.sample.ui

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kartic.blemagic.sample.R
import com.kartic.blemagic.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("BLE_PREFS", Context.MODE_PRIVATE)
    }

    private val layoutBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)

        if (sharedPreferences.getString("SHOWED_ALERT_DIALOG", null) == null) {
            saveToPref()
            showAlertDialog()
        }

//        clearAll()

        layoutBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        layoutBinding.bottomNavigationView.selectedItemId = R.id.action_scan
    }

    private fun clearAll() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun saveToPref() {
        val editor = sharedPreferences.edit()
        editor.putString("SHOWED_ALERT_DIALOG", "SHOWED_ALERT_DIALOG")
        editor.apply()

        editor.clear()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Message")
        builder.setMessage("This is the sample app to demonstrate how you can use this library for scanning and pairing Bluetooth LE Devices. This sample app doesn't have necessary permissions. Go to the settings and provide necessary permissions.")

        // Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })

        // Create and show the alert dialog
        val dialog: AlertDialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_scan -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    layoutBinding.fragmentContainer.id,
                    ScanDeviceFragment(),
                    ScanDeviceFragment::class.java.name
                )

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.commitAllowingStateLoss()
                supportFragmentManager.executePendingTransactions()
            }

            R.id.action_paired -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    layoutBinding.fragmentContainer.id,
                    PairedDevicesFragment(),
                    PairedDevicesFragment::class.java.name
                )

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.commitAllowingStateLoss()
                supportFragmentManager.executePendingTransactions()
            }
        }
        return true
    }

    override fun onDestroy() {
        MyBleManager.getInstance(this@MainActivity).nukeSession()
        super.onDestroy()
    }

}