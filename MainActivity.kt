package com.no1.dnsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ipv4Input = findViewById<EditText>(R.id.ipv4_input)
        val ipv6Input = findViewById<EditText>(R.id.ipv6_input)
        val dnsInput = findViewById<EditText>(R.id.dns_input)
        val connectButton = findViewById<Button>(R.id.connect_button)

        connectButton.setOnClickListener {
            val ipv4 = ipv4Input.text.toString()
            val ipv6 = ipv6Input.text.toString()
            val dns = dnsInput.text.toString()

            val intent = Intent(this, MyVpnService::class.java)
            intent.putExtra("IPv4", ipv4)
            intent.putExtra("IPv6", ipv6)
            intent.putExtra("DNS", dns)
            startService(intent)
        }
    }
}
