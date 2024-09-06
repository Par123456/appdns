package com.no1.dnsapp

import android.net.VpnService
import android.os.ParcelFileDescriptor
import java.net.InetSocketAddress
import java.nio.channels.DatagramChannel

class MyVpnService : VpnService() {

    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ipv4 = intent?.getStringExtra("IPv4")
        val ipv6 = intent?.getStringExtra("IPv6")
        val dns = intent?.getStringExtra("DNS")

        setupVpn(ipv4, ipv6, dns)

        return START_STICKY
    }

    private fun setupVpn(ipv4: String?, ipv6: String?, dns: String?) {
        val builder = Builder()

        // تنظیم DNS
        dns?.let {
            builder.addDnsServer(it)
        }

        // تنظیم آدرس های IPv4 و IPv6
        ipv4?.let {
            builder.addAddress(it, 24)
        }

        ipv6?.let {
            builder.addAddress(it, 64)
        }

        // تنظیم رابط شبکه
        builder.setSession("MyVPN")
        builder.setMtu(1500)

        // تنظیم روتر پیش‌فرض
        builder.addRoute("0.0.0.0", 0)
        builder.addRoute("::", 0)

        vpnInterface = builder.establish()

        // باز کردن کانال برای ارسال داده‌ها
        val tunnel = DatagramChannel.open()
        tunnel.connect(InetSocketAddress("8.8.8.8", 8080))
    }

    override fun onDestroy() {
        vpnInterface?.close()
        super.onDestroy()
    }
}
