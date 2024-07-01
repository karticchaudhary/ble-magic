package com.kartic.blemagic

enum class ScanModes(val id: Int) {

    // Scans only when other apps are scanning, consuming the least power.
    SCAN_MODE_OPPORTUNISTIC(-1),
    // Performs BLE scans at a low duty cycle to save power.
    SCAN_MODE_LOW_POWER(0),
    // Offers a balanced approach between scan frequency and power consumption.
    SCAN_MODE_BALANCED(1),
    // Scans frequently to minimize latency, consuming the most power.
    SCAN_MODE_LOW_LATENCY(2);

}