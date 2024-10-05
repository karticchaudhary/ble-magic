package com.kartic.ble_magic

enum class BluetoothPeripheralState(val state: Int) {

    STATE_DISCONNECTED(0),
    STATE_CONNECTING(1),
    STATE_CONNECTED(2),
    STATE_DISCONNECTING(3),

    BOND_BONDING(11),
    BOND_BONDED(12),
    BOND_NONE(10);

}