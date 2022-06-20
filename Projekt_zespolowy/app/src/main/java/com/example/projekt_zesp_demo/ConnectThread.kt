package com.example.projekt_zesp_demo

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*



class ConnectThread(private val device: BluetoothDevice, private val listener: ReceiveThread.Listener):Thread() {
    val uuid = "00001101-0000-1000-8000-00805F9B34FB" //sprawdzic o co z tym chodzi
    var mSocket: BluetoothSocket? = null
    lateinit var rThread: ReceiveThread

    init {
        try {
            mSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(uuid))
        }catch (i:IOException){

        }
    }

    override fun run() {
        try {
            listener.onReceive("Connecting...")
            mSocket?.connect()
            listener.onReceive("Connected to the device")
            rThread = ReceiveThread(mSocket!!, listener)
            rThread.start()
        }catch (i:IOException){
            listener.onReceive("Cannot connect to the device")
            closeConnection()
        }
    }

    fun closeConnection(){
        try {
            mSocket?.close()
        }catch (i:IOException){

        }
    }
}