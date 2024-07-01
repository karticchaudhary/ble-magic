package com.kartic.blemagic.sample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kartic.blemagic.BluetoothPeripheral
import com.kartic.blemagic.sample.databinding.ItemBluetoothListBinding

class BluetoothListRecyclerAdapter(private val listener: BluetoothListRecyclerAdapterClickListener? = null) :
    RecyclerView.Adapter<BluetoothListRecyclerAdapter.MyViewHolder>() {

    private val peripheralList = mutableListOf<BluetoothPeripheral>()
    private val map: LinkedHashMap<String, BluetoothPeripheral> = LinkedHashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemBluetoothListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = peripheralList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(peripheralList[position])
    }

    inner class MyViewHolder(private val binding: ItemBluetoothListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(deviceData: BluetoothPeripheral) {
            val result =
                "Device Name: ${deviceData.name},\nStrength: ${deviceData.rssi},\nAddress: ${deviceData.address}"
            binding.deviceDetails.text = result

            binding.detailsContainer.setOnClickListener {
                listener?.onItemClicked(deviceData)
            }
        }

    }

    // LIST SHOULD BE SORTED
    fun updatePeripheral(peripheral: BluetoothPeripheral) {
        if (map.containsKey(peripheral.address)) {
            val device = map[peripheral.address]
            device?.rssi = peripheral.rssi
        } else {
            map[peripheral.address.toString()] = peripheral
        }
        peripheralList.clear()
        peripheralList.addAll(map.values.toList())
        notifyDataSetChanged()
    }

    fun updatePeripheralList(mutableList: MutableList<BluetoothPeripheral>) {
        peripheralList.clear()
        peripheralList.addAll(mutableList)
        notifyDataSetChanged()
    }

}

interface BluetoothListRecyclerAdapterClickListener {
    fun onItemClicked(peripheral: BluetoothPeripheral)
}