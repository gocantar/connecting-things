package com.example.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.extension.getPlugSerciceUuid
import com.example.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.example.gocantar.connectingthings.device.plug.RevogiPlug
import com.example.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData

/**
 * Created by gocantar on 11/1/18.
 */
class PlugController : PlugControllerBoundary {

    private val TAG = javaClass.simpleName

    override fun turnOn(gatt: BluetoothGatt) {
        val service: ParcelUuid = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE) ->
                    RevogiPlug.turnOn(gatt)
        }
    }

    override fun turnOff(gatt: BluetoothGatt) {
        val service: ParcelUuid = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE) ->
                RevogiPlug.turnOff(gatt)
        }
    }

    override fun requestPowerConsumption(gatt: BluetoothGatt) {
        val service: ParcelUuid = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE) ->
                RevogiPlug.requestPowerConsumption(gatt)
        }
    }

    override fun decodePowerConsumption(gatt: BluetoothGatt, charData: CharacteristicData): Int {
        val service: ParcelUuid = getServiceUuid(gatt)
        return when(service){
            ParcelUuid(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE) ->
                RevogiPlug.decodePowerConsumption(charData)
            else -> 0
        }
    }

    override fun enableNotifications(gatt: BluetoothGatt) {
        val service: ParcelUuid = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE) ->
                RevogiPlug.enableNotifications(gatt)
        }
    }

    override fun disableNotifications(gatt: BluetoothGatt) {
        val service: ParcelUuid = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE) ->
                RevogiPlug.disableNotifications(gatt)
        }
    }

    private fun getServiceUuid(gatt: BluetoothGatt): ParcelUuid{
        return gatt.services.map { ParcelUuid(it.uuid) }.getPlugSerciceUuid()
    }

}