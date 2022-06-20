package com.example.projekt_zesp_demo

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.projekt_zesp_demo.databinding.ActivityControlBinding

class ControlActivity : AppCompatActivity(), ReceiveThread.Listener {
    private lateinit var binding: ActivityControlBinding
    private lateinit var actListLauncher: ActivityResultLauncher<Intent>
    lateinit var btConnection: BtConnection
    private var listItem: ListItem? = null
    private lateinit var tv_user_id: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_control)
        setContentView(binding.root)
        onBtListResult()
        init()
        tv_user_id = findViewById(R.id.tv_user_id)
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        tv_user_id.text = "User ID :: $userId"

        /*switch_lights_red = findViewById(R.id.sw_red)
        switch_lights_green = findViewById(R.id.sw_green)
        switch_lights_blue = findViewById(R.id.sw_blue)*/

        binding.apply {
            btnKitchenRedControl.setOnClickListener{
                btConnection.sendMessage("g")
            }
            btnKitchenGreenControl.setOnClickListener{
                btConnection.sendMessage("h")
            }
            btnKitchenBlueControl.setOnClickListener{
                btConnection.sendMessage("i")
            }
            btnKitchenLightsoffControl.setOnClickListener{
                btConnection.sendMessage("j")
            }

            /*----------------------------------------*/

            btnBedroom1RedControl.setOnClickListener{
                btConnection.sendMessage("o")
            }
            btnBedroom1GreenControl.setOnClickListener{
                btConnection.sendMessage("p")
            }
            btnBedroom1BlueControl.setOnClickListener{
                btConnection.sendMessage("q")
            }
            btnBedroom1LightsoffControl.setOnClickListener{
                btConnection.sendMessage("r")
            }

            /*-----------------------------------------------*/

            btnBedroom2RedControl.setOnClickListener{
                btConnection.sendMessage("s")
            }
            btnBedroom2GreenControl.setOnClickListener{
                btConnection.sendMessage("t")
            }
            btnBedroom2BlueControl.setOnClickListener{
                btConnection.sendMessage("u")
            }
            btnBedroom2LightsoffControl.setOnClickListener{
                btConnection.sendMessage("w")
            }

            /*-------------------------------------------------*/

            btnToiletRedControl.setOnClickListener{
                btConnection.sendMessage("k")
            }
            btnToiletGreenControl.setOnClickListener{
                btConnection.sendMessage("l")
            }
            btnToiletBlueControl.setOnClickListener{
                btConnection.sendMessage("m")
            }
            btnToiletLightsoffControl.setOnClickListener{
                btConnection.sendMessage("n")
            }

            /*-------------------------------------------------*/

            btnGarageRedControl.setOnClickListener{
                btConnection.sendMessage("a")
            }
            btnGarageGreenControl.setOnClickListener{
                btConnection.sendMessage("b")
            }
            btnGarageBlueControl.setOnClickListener{
                btConnection.sendMessage("c")
            }
            btnGarageBlueControl.setOnClickListener{
                btConnection.sendMessage("d")
            }

            /*-------------------------------------------------*/

            btnParkingControl.setOnClickListener{
                btConnection.sendMessage("e")
            }
            btnParkingControlOff.setOnClickListener{
                btConnection.sendMessage("f")
            }
            btnGarageGateControl.setOnClickListener{
                btConnection.sendMessage("1")
            }
            btnGarageGateControlOff.setOnClickListener{
                btConnection.sendMessage("2")
            }

            /*----------------------------------------------------------*/



            /*switch_lights_red.setOnCheckedChangeListener{CompoundButton, isChecked ->
                if(isChecked) {
                    btConnection.sendMessage("R")
                    switch_lights_red.setBackgroundColor(Color.RED)
                    Toast.makeText(this@Con`trolActivity, "Włączyłeś czerwone światło", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@ControlActivity, "Wyłączyłeś czerwone światło", Toast.LENGTH_SHORT).show()
                }
            }
            switch_lights_green.setOnCheckedChangeListener{CompoundButton, isChecked ->
                if(isChecked) {
                    btConnection.sendMessage("G")
                    switch_lights_green.setBackgroundColor(Color.GREEN)
                    Toast.makeText(this@ControlActivity, "Włączyłeś zielone światło", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@ControlActivity, "Wyłączyłeś zielone światło", Toast.LENGTH_SHORT).show()
                }
            }
            switch_lights_blue.setOnCheckedChangeListener{CompoundButton, isChecked ->
                if(isChecked) {
                    btConnection.sendMessage("B")
                    switch_lights_blue.setBackgroundColor(Color.BLUE)
                    Toast.makeText(this@ControlActivity, "Włączyłeś niebieskie światło", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@ControlActivity, "Wyłączyłeś niebieskie światło", Toast.LENGTH_SHORT).show()
                }
            }*/
        }
    }

    private fun init(){
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        btConnection = BtConnection(btAdapter,this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.control_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_list){
            actListLauncher.launch(Intent(this,MainActivity::class.java))
        } else if(item.itemId == R.id.id_connect){
            listItem.let {
                btConnection.connect(it?.mac!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun onBtListResult(){
        actListLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                listItem = it.data?.getSerializableExtra(MainActivity.DEVICE_KEY) as ListItem
            }
        }
    }

    override fun onReceive(message: String) {
        runOnUiThread{
            binding.tvMessage.text = message
        }
    }
}