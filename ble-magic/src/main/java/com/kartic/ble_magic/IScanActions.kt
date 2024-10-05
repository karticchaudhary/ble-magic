package com.kartic.ble_magic

import androidx.annotation.NonNull

interface IScanActions {

    fun scan(@NonNull scanCallBack: IScanCallBack)

    fun customScanMode(@NonNull scanModes: ScanModes, @NonNull scanCallBack: IScanCallBack)
    fun customMacAddressScan(@NonNull macAddress: String, @NonNull scanCallBack: IScanCallBack)
    fun customDeviceNameScan(@NonNull deviceName: String, @NonNull scanCallBack: IScanCallBack)
    fun customUUIDScan(@NonNull uuid: String, @NonNull scanCallBack: IScanCallBack)

    fun customMacAddressScan(
        @NonNull macAddress: String,
        @NonNull scanModes: ScanModes,
        @NonNull scanCallBack: IScanCallBack
    )

    fun customDeviceNameScan(
        @NonNull deviceName: String,
        @NonNull scanModes: ScanModes,
        @NonNull scanCallBack: IScanCallBack
    )

    fun customUUIDScan(
        @NonNull uuid: String,
        @NonNull scanModes: ScanModes,
        @NonNull scanCallBack: IScanCallBack
    )

}