package com.kna.flashvibrate

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kna.flashvibrate.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appPreferences :AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences(applicationContext)
        appPreferences = AppPreferences(applicationContext)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autoText()


        binding.btn1.setOnClickListener {
            AudioUtil.cutAudio(
                this,
                5,
                15,
                "/storage/emulated/0/Download/Chua Bao Gio - Trung Quan.mp3"
            ) {
                Log.e("cut_audio", it)
            }
        }
        binding.btn2.setOnClickListener {
            RecordUtil.startRecord(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/new_record3.mp3"
            )
        }
        binding.btn3.setOnClickListener {
            RecordUtil.stopRecording()
        }
        binding.btn4.setOnClickListener {
        }
        binding.btn5.setOnClickListener {
        }
        binding.btn6.setOnClickListener {
            binding.tvVibrate.text = "Strong"
            VibrateFlashThread(this, MODE_VIBRATE_1).start()
        }
        binding.btn7.setOnClickListener {
            binding.tvVibrate.text = "heart"
            VibrateFlashThread(this, MODE_VIBRATE_2).start()

        }
        binding.btn8.setOnClickListener {
            binding.tvVibrate.text = "tick_toc"
            VibrateFlashThread(this, MODE_VIBRATE_3).start()
        }
        binding.btn9.setOnClickListener {
            binding.tvFlash.text = "SOS"
            VibrateFlashThread(this, MODE_FLASH_1).start()
        }
        binding.btn10.setOnClickListener {
            binding.tvFlash.text = "DISCO"
            VibrateFlashThread(this, MODE_FLASH_2).start()

        }
        binding.btn11.setOnClickListener {
            VibrateFlashThread.stopFlash()
        }
        binding.btn12.setOnClickListener {
            VibrateFlashThread.stopVibrate()
        }
        binding.btn13.setOnClickListener {
            VibrateFlashThread(this, appPreferences.currentFlash).start()
            VibrateFlashThread(this, appPreferences.currentVibrate).start()
        }
        binding.btn14.setOnClickListener {
            VibrateFlashThread.stopAll()
        }
    }

    private fun autoText() {
        when (appPreferences.currentFlash) {
            MODE_FLASH_1 -> {
                binding.tvFlash.text = "SOS"
            }

            MODE_FLASH_2 -> {
                binding.tvFlash.text = "DISCO"
            }
        }
        when (appPreferences.currentVibrate) {
            MODE_VIBRATE_1 -> {
                binding.tvVibrate.text = "Strong"
            }

            MODE_VIBRATE_2 -> {
                binding.tvVibrate.text = "Heart"
            }

            MODE_VIBRATE_3 -> {
                binding.tvVibrate.text = "TickTok"
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        RecordUtil.stopRecording()
        VibrateFlashThread.stopAll()
    }

}