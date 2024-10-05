package com.kartic.blemagic.sample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kartic.ble_magic.BleManager
import com.kartic.ble_magic.BluetoothPeripheral
import com.kartic.ble_magic.BluetoothPeripheralState
import com.kartic.ble_magic.BluetoothPeripheralStateListener
import com.kartic.blemagic.sample.databinding.ActivityDetailedDeviceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedDeviceActivity : AppCompatActivity() {

    private val layoutBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityDetailedDeviceBinding.inflate(layoutInflater)
    }

    private val bleManager: BleManager by lazy {
        MyBleManager.getInstance(this@DetailedDeviceActivity).bleManager
    }

    private var peripheral: BluetoothPeripheral? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)

        intent.getParcelableExtra<BluetoothPeripheral>(BLUETOOTH_PERIPHERAL_DATA)?.let {
            peripheral = it
            setPeripheralData(it)
        }

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setPeripheralData(peripheral: BluetoothPeripheral) {
        layoutBinding.deviceName.text = "Device Name: ${peripheral.name.toString()}"
        layoutBinding.deviceAddress.text = "Device Address: ${peripheral.address.toString()}"
        layoutBinding.deviceStrength.text = "Device Strength: ${peripheral.rssi.toString()}"

        layoutBinding.deviceBondState.text = peripheral.bondState?.let { bondState(it) }

        layoutBinding.appBar.title.text = peripheral.name.toString()
    }

    private fun bondState(state: Int): String {
        val bondState = when (state) {
            BluetoothPeripheralState.BOND_NONE.state -> "Bond State: BOND_NONE (Not bonded)"
            BluetoothPeripheralState.BOND_BONDING.state -> "Bond State: BOND_BONDING (Bonding in progress)"
            BluetoothPeripheralState.BOND_BONDED.state -> {
                layoutBinding.devicePairButton.visibility = View.GONE
                "Bond State: BOND_BONDED (Bonded)"
            }
            else -> {
                "UNKNOWN Device State"
            }
        }

        layoutBinding.deviceBondState.text = bondState
        return bondState
    }

    private fun connectingState(state: Int) {
        layoutBinding.connectingStateTv.text = when (state) {
            BluetoothPeripheralState.STATE_CONNECTING.state -> "Connecting State: STATE_CONNECTING"
            BluetoothPeripheralState.STATE_CONNECTED.state -> "Connecting State: STATE_CONNECTED"
            BluetoothPeripheralState.STATE_DISCONNECTING.state -> "Connecting State: STATE_DISCONNECTING"
            BluetoothPeripheralState.STATE_DISCONNECTED.state -> "Connecting State: STATE_DISCONNECTED"
            else -> "Connecting State: UNKNOWN"
        }
    }

    private fun initViews() {
        layoutBinding.devicePairButton.setOnClickListener {
            bleManager.pairDeviceByMacAddress(
                peripheral?.address.toString(),
                bluetoothPeripheralStateListener
            )
        }
        layoutBinding.appBar.icBack.setOnClickListener {
            finish()
        }
    }

    private val bluetoothPeripheralStateListener = object : BluetoothPeripheralStateListener {
        override fun onConnectionStateChanged(state: BluetoothPeripheralState) {
            lifecycleScope.launch(Dispatchers.Main) {
                connectingState(state.state)
            }
        }

        override fun onBondStateChanged(state: BluetoothPeripheralState) {
            lifecycleScope.launch(Dispatchers.Main) {
                bondState(state.state)
            }
        }
    }

}