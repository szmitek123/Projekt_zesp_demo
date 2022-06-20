package com.example.projekt_zesp_demo

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ReceiveThread(val bSocket: BluetoothSocket, val listener: Listener): Thread() {
    var inStream: InputStream? = null
    var outStream: OutputStream? = null

    init {
        try {
            inStream=bSocket.inputStream
        }catch (i: IOException){

        }
        try {
            outStream=bSocket.outputStream
        }catch (i: IOException){

        }
    }

    override fun run(){
        val buf = ByteArray(256)
        while (true){
            try{
                val size = inStream?.read(buf)
                val message = String(buf,0,size!!)
                listener.onReceive(message)
            }catch (i:IOException){
                listener.onReceive("Connection lost")
                break
            }
        }
    }

    fun sendMessage(byteArray: ByteArray){
        try {
            outStream?.write(byteArray)
        }catch (i:IOException){
        }
    }
    interface Listener{
        fun onReceive(message: String)
    }
}