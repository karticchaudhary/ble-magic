package com.kartic.blemagic

interface BluetoothPeripheralStateListener {

    fun onConnectionStateChanged(state : BluetoothPeripheralState)

    fun onBondStateChanged(state : BluetoothPeripheralState)

}