package com.kartic.ble_magic

interface BluetoothPeripheralStateListener {

    fun onConnectionStateChanged(state : BluetoothPeripheralState)

    fun onBondStateChanged(state : BluetoothPeripheralState)

}