package com.example.projekt_zesp_demo

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekt_zesp_demo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity(), RcAdapter.Listener {
    private var btAdapter: BluetoothAdapter? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RcAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)
        init()

        lateinit var btn_logout: Button

        //val CITY: String = "warsaw,pl"
        //val API: String = "06c921750b9a82d8f5d1294e1586276f"

        btn_logout = findViewById(R.id.btn_logout)


        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")



        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            finish()
        }

        lateinit var tv_kitchen: TextView
        tv_kitchen = findViewById(R.id.tv_kitchen)

        tv_kitchen.setOnClickListener {
            startActivity(Intent(this@MainActivity, KitchenActivity::class.java))
        }

        lateinit var tv_garage: TextView
        tv_garage = findViewById(R.id.tv_garage)

        tv_garage.setOnClickListener {
            startActivity(Intent(this@MainActivity, GarageActivity::class.java))
        }

        lateinit var tv_bedroom1: TextView
        tv_bedroom1 = findViewById(R.id.tv_bedroom1)

        tv_bedroom1.setOnClickListener {
            startActivity(Intent(this@MainActivity, Bedroom1Activity::class.java))
        }

        lateinit var tv_toilet: TextView;
        tv_toilet = findViewById(R.id.tv_toilet)

        tv_toilet.setOnClickListener{
            startActivity(Intent(this@MainActivity, ToiletActivity::class.java))
        }

        lateinit var tv_bedroom2: TextView
        tv_bedroom2 = findViewById(R.id.tv_bedroom2)

        tv_bedroom2.setOnClickListener {
            startActivity(Intent(this@MainActivity, Bedroom2Activity::class.java))
        }
    }
    private fun init(){
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
        adapter = RcAdapter(this)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter
        getPairedDevices()
    }


    private fun getPairedDevices(){
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        val tempList = ArrayList<ListItem>()
        pairedDevices?.forEach{
            tempList.add(ListItem(it.name,it.address))
        }
        adapter.submitList(tempList)
    }

    companion object{
        const val DEVICE_KEY = "device_key"
    }

    override fun onClick(item: ListItem) {
        val i = Intent().apply{
            putExtra(DEVICE_KEY,item)
        }
        setResult(RESULT_OK,i)
        finish()
    }

}