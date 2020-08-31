package app.examples.socket

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import app.example.socket.R
import org.json.JSONObject
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI
import java.net.URISyntaxException


class MainActivity : AppCompatActivity() {
    private lateinit var webSocketClient: WebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createWebSocketClient()
    }


    private fun createWebSocketClient() {
        Log.d(TAG, "createWebSocketClient: ")
        val uri: URI
        uri = try {
            // Connect to local host
            URI("ws://192.168.0.105:8080/message")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return
        }
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen() {
                Log.d(TAG, "Session is starting")
                val json = JSONObject()
                json.put("user", "Supto")

                webSocketClient.send(json.toString())
            }

            override fun onTextReceived(s: String) {
                Log.d(TAG, "Message received")

            }

            override fun onBinaryReceived(data: ByteArray) {
                Log.d(TAG, "onBinaryReceived: ")
            }

            override fun onPingReceived(data: ByteArray) {
                Log.d(TAG, "onPingReceived: ")
            }

            override fun onPongReceived(data: ByteArray) {
                Log.d(TAG, "onPongReceived: ")
            }

            override fun onException(e: Exception) {
                e.printStackTrace()
            }

            override fun onCloseReceived() {
                Log.d(TAG, "Closed ")
                println("onCloseReceived")
            }
        }
        webSocketClient.setConnectTimeout(10000)
        webSocketClient.setReadTimeout(60000)
        webSocketClient.enableAutomaticReconnection(5000)
        webSocketClient.connect()
    }

    companion object {
        private const val TAG = "MainActivity"
    }


}