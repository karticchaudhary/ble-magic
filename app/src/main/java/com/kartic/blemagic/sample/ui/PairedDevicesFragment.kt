package com.kartic.blemagic.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kartic.ble_magic.BleManager
import com.kartic.blemagic.sample.databinding.FragmentPairedDevicesBinding

class PairedDevicesFragment : Fragment() {

    private lateinit var layoutBinding: FragmentPairedDevicesBinding

    private val recyclerViewAdapter by lazy {
        BluetoothListRecyclerAdapter()
    }

    private val bleManager: BleManager by lazy {
        MyBleManager.getInstance(requireActivity()).bleManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutBinding = FragmentPairedDevicesBinding.inflate(layoutInflater, container, false)
        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        isDeviceListFound()
    }

    private fun initRecyclerView() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        layoutBinding.recyclerView.addItemDecoration(dividerItemDecoration)

        layoutBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        layoutBinding.recyclerView.adapter = recyclerViewAdapter
    }

    private fun isDeviceListFound() {
        val list = bleManager.getBondedDevices().toMutableList()
        recyclerViewAdapter.updatePeripheralList(list)

        when (list.size) {
            0 -> {
                layoutBinding.pairedDeviceTv.visibility = View.VISIBLE
                layoutBinding.recyclerView.visibility = View.GONE
            }

            else -> {
                layoutBinding.pairedDeviceTv.visibility = View.GONE
                layoutBinding.recyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}