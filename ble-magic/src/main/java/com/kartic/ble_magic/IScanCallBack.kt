package com.kartic.blemagic

import android.bluetooth.le.ScanResult
import androidx.annotation.NonNull

interface IScanCallBack {
    fun onScanResults(@NonNull peripheral: BluetoothPeripheral, @NonNull scanResult: ScanResult)
    fun onScanFailed(errorCode: Int)
}