# Android GATT BLE Library

Welcome to the Android GATT BLE Library! This library simplifies the process of working with Bluetooth Low Energy (BLE) devices using the Generic Attribute Profile (GATT) on Android. It provides functionalities for scanning and pairing with BLE devices, making it easier to integrate BLE features into your Android applications.

# Features
- BLE Device Scanning: Scan for nearby BLE devices with customizable filters.
- Device Pairing: Pair with BLE devices securely.
- Callback Interfaces: Receive callbacks for various BLE events.

For convenience, sample app is also included.

# Installation

To use this library in your Android project, add the following dependency to your build.gradle file:
```
allprojects {
		repositories {
                        ......
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

dependencies {
        implementation 'com.github.karticchaudhary:ble-magic:1.0'
}
```
