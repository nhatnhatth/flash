package com.kna.flashvibrate

import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Environment
import android.os.Vibrator
import android.provider.MediaStore
import android.provider.MediaStore.Audio
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.kna.flashvibrate.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var thread: Thread = Thread()
    private var isRunning = false
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
            AudioUtil.cutAudio(this, 5, 15, "/storage/emulated/0/Download/Chua Bao Gio - Trung Quan.mp3") {
                Log.e("nnnnnnnnnnnnnn", it)
            }
        }
        binding.btn2.setOnClickListener {
            RecordUtil.startRecord(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/new_record3.mp3")
        }
        binding.btn3.setOnClickListener {
            RecordUtil.stopRecording()
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

    override fun onDestroy() {
        super.onDestroy()
        RecordUtil.stopRecording()
        VibarateFlashThread.stopAll()
    }

}