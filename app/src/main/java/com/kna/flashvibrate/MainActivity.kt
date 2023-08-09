package com.kna.flashvibrate

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.nfc.Tag
import android.os.Bundle
import android.os.Vibrator
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
            switchFlashLight(600)
        }
        binding.btn2.setOnClickListener {
            switchFlashLight(100)
        }
        binding.btn3.setOnClickListener {
            vibrate(700, 150)
        }
        binding.btn4.setOnClickListener {
            vibrate(0, 1500)
        }
        binding.btn5.setOnClickListener {
            vibrate(1000, 100)
        }
        binding.btn6.setOnClickListener {
            VibarateFlashThread(this, VibarateFlashThread.VIBRATE_MODE, 1500, 0).start()
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
    }

    private fun stopThread() {
    }

    private fun vibrate(h: Int, x: Int) {
        isRunning = true
        thread = Thread {
            try {
                if (h == 0) {
                    vibrator.vibrate(x.toLong())
                } else {
                    for (i in 0..9) {
                        vibrator.vibrate(x.toLong())
                        Thread.sleep((h + x).toLong())
                    }
                }
                isChecked = !isChecked
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
        }
        thread.start()
    }

    private fun switchFlashLight(h: Int) {
        isRunning = true
        thread = Thread {
            try {
                for (i in 0 until 6000 / h) {
                    cameraId?.let { manager.setTorchMode(it, true) }
                    Thread.sleep(h.toLong())
                    cameraId?.let { manager.setTorchMode(it, false) }
                    Thread.sleep(h.toLong())
                }
                isChecked = !isChecked
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
        }
        thread.start()
    }
}