package com.kartic.blemagic

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.ParcelUuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Objects
import java.util.UUID
import kotlin.coroutines.CoroutineContext


@SuppressLint("MissingPermission")
class BleManager(private val context: Context) : IScanActions {

    private val coroutineCtx: CoroutineContext = Dispatchers.Main
    private val job: CoroutineScope = CoroutineScope(coroutineCtx)

    private var bluetoothScanner: BluetoothLeScanner? = null
    private val mapPairCodeByMacAddress = mutableMapOf<String, String>()
    private var uiScanCallback: IScanCallBack? = null
    private var bluetoothPeripheralStateListener: BluetoothPeripheralStateListener? = null


    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val adapter = initAdapter()
        bluetoothScanner = adapter?.bluetoothLeScanner
        adapter
    }

    private fun initAdapter(): BluetoothAdapter? {
        val manager = Objects.requireNonNull(
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager,
            "Bluetooth Service not available"
        )
        return manager.adapter
    }

    override fun scan(scanCallBack: IScanCallBack) {
        if (checkPermissions()) {
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getDefaultScanFilters(),
                    getDefaultScanSettings(ScanModes.SCAN_MODE_LOW_LATENCY),
                    nativeApiScanCallback
                )
            }
        } else {
            job.cancel()
        }
    }

    override fun customScanMode(scanModes: ScanModes, scanCallBack: IScanCallBack) {
        if (checkPermissions()) {
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getDefaultScanFilters(),
                    getDefaultScanSettings(scanModes),
                    nativeApiScanCallback
                )
            }
        } else {
            job.cancel()
        }
    }

    override fun customMacAddressScan(macAddress: String, scanCallBack: IScanCallBack) {
        if (checkPermissions()) {
            if (!BluetoothAdapter.checkBluetoothAddress(macAddress)) {
                throw IllegalArgumentException("Wrong mac address format.")
            }
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getCustomMacAddressFilter(macAddress),
                    getDefaultScanSettings(ScanModes.SCAN_MODE_BALANCED),
                    nativeApiScanCallback
                )
            }
        } else {
            job.cancel()
        }
    }

    override fun customMacAddressScan(
        macAddress: String,
        scanModes: ScanModes,
        scanCallBack: IScanCallBack
    ) {
        if (checkPermissions()) {
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getCustomMacAddressFilter(macAddress),
                    getDefaultScanSettings(scanModes),
                    nativeApiScanCallback
                )
            }
        } else {
            job.cancel()
        }
    }

    override fun customDeviceNameScan(deviceName: String, scanCallBack: IScanCallBack) {
        if (checkPermissions()) {
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getCustomDeviceNameFilter(deviceName),
                    getDefaultScanSettings(ScanModes.SCAN_MODE_BALANCED),
                    nativeApiScanCallback
                )
            }
        } else {
            job.cancel()
        }
    }

    override fun customDeviceNameScan(
        deviceName: String,
        scanModes: ScanModes,
        scanCallBack: IScanCallBack
    ) {
        if (checkPermissions()) {
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getCustomDeviceNameFilter(deviceName),
                    getDefaultScanSettings(scanModes),
                    nativeApiScanCallback
                )
            }
        } else {
            job.cancel()
        }
    }

    override fun customUUIDScan(uuid: String, scanCallBack: IScanCallBack) {
        if (checkPermissions()) {
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getCustomUUIDFilter(uuid),
                    getDefaultScanSettings(ScanModes.SCAN_MODE_BALANCED),
                    nativeApiScanCallback
                )
            }
        } else {
            job.cancel()
        }
    }

    override fun customUUIDScan(
        uuid: String, scanModes: ScanModes,
        scanCallBack: IScanCallBack
    ) {
        if (checkPermissions()) {
            this.uiScanCallback = scanCallBack

            job.launch {
                if (nativeApiScanCallback != null) {
                    bluetoothScanner?.stopScan(nativeApiScanCallback)
                }
                bluetoothAdapter?.cancelDiscovery()
                delay(1000)

                bluetoothScanner?.startScan(
                    getCustomUUIDFilter(uuid),
                    getDefaultScanSettings(scanModes),
                    this@BleManager.nativeApiScanCallback
                )

            }
        } else {
            job.cancel()
        }
    }

    private var nativeApiScanCallback: ScanCallback? = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result?.let {
                uiScanCallback?.onScanResults(
                    BluetoothPeripheral(
                        name = it.device?.name,
                        address = it.device?.address,
                        bondState = it.device?.bondState,
                        type = it.device?.type,
                        rssi = it.rssi,
                    ),
                    it
                )
            }
        }

        override fun onScanFailed(errorCode: Int) {
            uiScanCallback?.onScanFailed(errorCode)
        }

    }

    private fun getDefaultScanFilters(): ArrayList<ScanFilter> {
        val list = ArrayList<ScanFilter>()
        list.add(
            ScanFilter.Builder()
                .build()
        )
        return list
    }

    private fun getCustomMacAddressFilter(macAddress: String): ArrayList<ScanFilter> {
        val list = ArrayList<ScanFilter>()
        list.add(
            ScanFilter.Builder()
                .setDeviceAddress(macAddress)
                .build()
        )
        return list
    }

    private fun getCustomDeviceNameFilter(deviceName: String): ArrayList<ScanFilter> {
        val list = ArrayList<ScanFilter>()
        list.add(
            ScanFilter.Builder()
                .setDeviceName(deviceName)
                .build()
        )
        return list
    }

    private fun getCustomUUIDFilter(uuid: String): ArrayList<ScanFilter> {
        val list = ArrayList<ScanFilter>()
        list.add(
            ScanFilter.Builder()
                .setServiceUuid(ParcelUuid((UUID.fromString(uuid))))
                .build()
        )
        return list
    }

    private fun getDefaultScanSettings(scanModes: ScanModes): ScanSettings {
        return ScanSettings.Builder()
            .setScanMode(scanModes.id)
            .setReportDelay(0)
            .setNumOfMatches((ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT))
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()
    }

    fun getBondedDevices(): Set<BluetoothPeripheral> {
        return HashSet<BluetoothPeripheral>().apply {
            bluetoothAdapter?.bondedDevices?.filter {
                it.type == BluetoothDevice.DEVICE_TYPE_LE
            }?.map {
                this.add(
                    BluetoothPeripheral(
                        it.name,
                        it.address, it.bondState, it.type
                    )
                )
            }
        }
    }

    fun stopScan() {
        job.launch {
            bluetoothScanner?.flushPendingScanResults(nativeApiScanCallback)
            bluetoothScanner?.stopScan(nativeApiScanCallback)
            bluetoothAdapter?.cancelDiscovery()
        }
    }

    fun nukeSession() {
        job.launch {
            bluetoothScanner?.stopScan(nativeApiScanCallback)
            bluetoothAdapter?.cancelDiscovery()
            bluetoothScanner = null
            nativeApiScanCallback = null
            uiScanCallback = null
            bluetoothPeripheralStateListener = null
            unregisterBroadcastReceivers()
        }
    }

    fun pairDeviceByMacAddressAndPairCode(
        macAddress: String,
        pairingCode: String,
        bluetoothPeripheralStateListener: BluetoothPeripheralStateListener
    ) {
        mapPairCodeByMacAddress[macAddress] = pairingCode
        pairDeviceByMacAddress(macAddress, bluetoothPeripheralStateListener)
    }

    fun pairDeviceByMacAddress(
        macAddress: String,
        bluetoothPeripheralStateListener: BluetoothPeripheralStateListener
    ) {
        this.bluetoothPeripheralStateListener = bluetoothPeripheralStateListener

        registerPairingAndBondingStatusBroadcastReceiver()

        job.launch {
            bluetoothAdapter?.getRemoteDevice(macAddress)
                ?.connectGatt(context, false, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
        }
    }

    private val bluetoothGattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)

            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    bluetoothPeripheralStateListener?.onConnectionStateChanged(
                        BluetoothPeripheralState.STATE_CONNECTED
                    )
                }

                BluetoothProfile.STATE_CONNECTING -> {
                    bluetoothPeripheralStateListener?.onConnectionStateChanged(
                        BluetoothPeripheralState.STATE_CONNECTING
                    )
                }

                BluetoothProfile.STATE_DISCONNECTING -> {
                    bluetoothPeripheralStateListener?.onConnectionStateChanged(
                        BluetoothPeripheralState.STATE_DISCONNECTING
                    )
                }

                BluetoothProfile.STATE_DISCONNECTED -> {
                    bluetoothPeripheralStateListener?.onConnectionStateChanged(
                        BluetoothPeripheralState.STATE_DISCONNECTED
                    )
                }
            }
        }
    }

    private fun registerPairingAndBondingStatusBroadcastReceiver() {
        context.registerReceiver(
            bondBroadcastReceiver,
            IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        )
    }

    private val bondBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED == intent?.action) {
                if (intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE) == null) return

                val currentState =
                    intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR)
                val prevState = intent.getIntExtra(
                    BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE,
                    BluetoothDevice.ERROR
                )
                manageBondState(currentState, prevState)
            }
        }
    }

    private fun manageBondState(currentState: Int, prevState: Int) {
        when (currentState) {
            BluetoothDevice.BOND_BONDING -> {
                bluetoothPeripheralStateListener?.onBondStateChanged(BluetoothPeripheralState.BOND_BONDING)
            }

            BluetoothDevice.BOND_BONDED -> {
                bluetoothPeripheralStateListener?.onBondStateChanged(BluetoothPeripheralState.BOND_BONDED)
            }

            BluetoothDevice.BOND_NONE -> {
                bluetoothPeripheralStateListener?.onBondStateChanged(BluetoothPeripheralState.BOND_NONE)
            }
        }
    }

    private fun unregisterBroadcastReceivers() {
        context.unregisterReceiver(bondBroadcastReceiver)
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (context.checkSelfPermission(android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                throw SecurityException("${android.Manifest.permission.BLUETOOTH_SCAN} permission not granted.")
            }
            if (context.checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                throw SecurityException("${android.Manifest.permission.BLUETOOTH_CONNECT} permission not granted")
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                throw SecurityException("${android.Manifest.permission.ACCESS_FINE_LOCATION} permission not granted")
            }
        } else {
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                throw SecurityException("${android.Manifest.permission.ACCESS_COARSE_LOCATION} permission not granted")
            }
        }
        return true
    }

    fun isBluetoothOn(): Boolean {
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        if (manager.adapter == null) return false
        return manager.adapter.isEnabled
    }

}