package com.kartic.blemagic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BluetoothPeripheral(
    val name: String? = null,
    val address: String? = null,
    val bondState: Int? = null,
    val type: Int? = null,
    var rssi: Int? = null,
    val primaryPhy: Int? = null,
    val secondaryPhy: Int? = null,
    val txPower: Int? = null,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BluetoothPeripheral

        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        return address?.hashCode() ?: 0
    }

}