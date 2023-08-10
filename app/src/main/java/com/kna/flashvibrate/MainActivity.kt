package com.kna.flashvibrate

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.nfc.Tag
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.kna.flashvibrate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var thread:Thread = Thread()
    private var  isRunning = false
    val manager: CameraManager by lazy {
        getSystemService(CAMERA_SERVICE) as CameraManager
    }
    private var cameraId: String? = null
    private var isChecked = true
    val vibrator: Vibrator by lazy {
        getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            cameraId = manager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        binding.btn1.setOnClickListener {
            AudioUtil.cutAudio(this, 5, 10, "/storage/emulated/0/Music/Recordings/Standard Recordings/persona 5.mp3", {
                Log.e("nnnnnnnnnnnnnn", it)
            })
        }
        binding.btn2.setOnClickListener {
        }
        binding.btn3.setOnClickListener {
        }
        binding.btn4.setOnClickListener {
        }
        binding.btn5.setOnClickListener {
        }
        binding.btn6.setOnClickListener {
            VibarateFlashThread(this, VibarateFlashThread.VIBRATE_MODE, 6000, 0).start()
        }
        binding.btn7.setOnClickListener {
            VibarateFlashThread(this, VibarateFlashThread.VIBRATE_MODE, 150, 700).start()

        }
        binding.btn8.setOnClickListener {
            VibarateFlashThread(this, VibarateFlashThread.VIBRATE_MODE, 100, 1000).start()
        }
        binding.btn9.setOnClickListener {
            VibarateFlashThread(this, VibarateFlashThread.FLASH_MODE, 600, 0).start()
        }
        binding.btn10.setOnClickListener {
            VibarateFlashThread(this, VibarateFlashThread.FLASH_MODE, 100, 0).start()

        }
        binding.btn11.setOnClickListener {
            VibarateFlashThread.stopFlash()
            cameraId?.let { manager.setTorchMode(it, false) }
        }
        binding.btn12.setOnClickListener {
            VibarateFlashThread.stopVibrate()
        }
        binding.btn13.setOnClickListener {
            VibarateFlashThread(this, VibarateFlashThread.VIBRATE_MODE, 6000, 0).start()
            VibarateFlashThread(this, VibarateFlashThread.FLASH_MODE, 600, 0).start()
        }
        binding.btn14.setOnClickListener {
            VibarateFlashThread.stopAll()
            vibrator.vibrate(0)
        }
    }
}