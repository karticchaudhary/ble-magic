package com.kartic.blemagic.sample.ui

import android.bluetooth.le.ScanResult
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kartic.ble_magic.BleManager
import com.kartic.ble_magic.BluetoothPeripheral
import com.kartic.ble_magic.IScanCallBack
import com.kartic.blemagic.sample.databinding.FragmentScanDeviceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val BLUETOOTH_PERIPHERAL_DATA = "BluetoothPeripheral"

class ScanDeviceFragment : Fragment() {

    private lateinit var layoutBinding: FragmentScanDeviceBinding

    private val recyclerViewAdapter by lazy {
        BluetoothListRecyclerAdapter(listener)
    }

    private val bleManager: BleManager by lazy {
        MyBleManager.getInstance(requireActivity()).bleManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutBinding = FragmentScanDeviceBinding.inflate(layoutInflater, container, false)
        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initRecyclerView()

        if (bleManager.isBluetoothOn()) {
            layoutBinding.recyclerView.visibility = View.GONE
            layoutBinding.scanningDeviceTv.visibility = View.VISIBLE
            layoutBinding.startScanBtn.visibility = View.GONE

            startScan()
        } else {
            layoutBinding.recyclerView.visibility = View.GONE
            layoutBinding.scanningDeviceTv.visibility = View.GONE
            layoutBinding.startScanBtn.visibility = View.VISIBLE
        }
    }

    private fun startScan() {
        bleManager.scan(object : IScanCallBack {
            override fun onScanResults(
                peripheral: BluetoothPeripheral,
                scanResult: ScanResult
            ) {
                lifecycleScope.launch(Dispatchers.Main) {
                if (null != peripheral.name && null != peripheral.address) {
                        isDeviceListFound(true)
                        recyclerViewAdapter.updatePeripheral(peripheral)
                    }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                lifecycleScope.launch(Dispatchers.Main) {
                isDeviceListFound(false)
                    val result = "Scan Failed. Error Code: $errorCode"
                    layoutBinding.scanningDeviceTv.text = result
                }
            }

        })
    }

    private fun initViews() {
        layoutBinding.startScanBtn.setOnClickListener {
            if (!bleManager.isBluetoothOn()) {
                Toast.makeText(requireActivity(), "Bluetooth is turned Off", Toast.LENGTH_LONG)
                    .show()
            } else {
                startScan()
            }
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        layoutBinding.recyclerView.addItemDecoration(dividerItemDecoration)

        layoutBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        layoutBinding.recyclerView.adapter = recyclerViewAdapter
    }

    fun isDeviceListFound(isFound: Boolean) {
        val result = "Scanning Devices"
        layoutBinding.scanningDeviceTv.text = result
        layoutBinding.startScanBtn.visibility = View.GONE

        when (isFound) {
            true -> {
                layoutBinding.scanningDeviceTv.visibility = View.GONE
                layoutBinding.recyclerView.visibility = View.VISIBLE
            }

            else -> {
                layoutBinding.scanningDeviceTv.visibility = View.VISIBLE
                layoutBinding.recyclerView.visibility = View.GONE
            }
        }
    }

    private fun resetBtn() {
        layoutBinding.recyclerView.visibility = View.GONE
        layoutBinding.scanningDeviceTv.visibility = View.GONE
        layoutBinding.startScanBtn.visibility = View.VISIBLE
    }

    private val listener = object : BluetoothListRecyclerAdapterClickListener {
        override fun onItemClicked(peripheral: BluetoothPeripheral) {
            bleManager.stopScan()
            requireActivity().startActivity(
                Intent(
                    requireContext(),
                    DetailedDeviceActivity::class.java
                ).apply {
                    putExtra(BLUETOOTH_PERIPHERAL_DATA, peripheral)
                }
            )
        }
    }

    override fun onDestroyView() {
        bleManager.stopScan()
        super.onDestroyView()
    }

}