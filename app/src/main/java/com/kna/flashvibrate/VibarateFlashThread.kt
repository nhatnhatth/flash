package com.kna.flashvibrate

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity

class VibarateFlashThread(var context: Context, var mode: Int, var duration: Int, var delay: Int) :
    Thread() {

    companion object {
        lateinit var vibrator: Vibrator
        fun stopAll() {
            stopVibrate()
            stopFlash()
        }

        fun stopVibrate() {
            vibrator.cancel()
            isRunningVibrate = false
        }

        fun stopFlash() {
            isRunningFlash = false
        }

        const val VIBRATE_MODE = 124
        const val FLASH_MODE = 126
        var isRunningFlash = false
        var isRunningVibrate = false
        var durationSum = 6000
    }

    override fun run() {
        if (mode == FLASH_MODE) {
            if (isRunningFlash) {
                stopFlash()
                sleep(100)
                doAction()
            } else {
                doAction()
            }
        } else {
            if (isRunningVibrate) {
                stopVibrate()
                sleep(100)
                doAction()
            } else {
                doAction()
            }
        }

    }

    private fun doAction() {
        if (mode == VIBRATE_MODE) {
            isRunningVibrate = true
            vibrate()
        } else if (mode == FLASH_MODE) {
            isRunningFlash = true
            switchFlash()
        }
    }

    private fun switchFlash() {
        var manager = context.getSystemService(AppCompatActivity.CAMERA_SERVICE) as CameraManager
        var cameraId = manager!!.cameraIdList[0]
        try {
            for (i in 0 until durationSum / 2 / duration) {
                cameraId?.let { manager.setTorchMode(it, true) }
                if (!startSleep(duration)) {
                    cameraId?.let { manager.setTorchMode(it, false) }
                    return
                }
                cameraId?.let { manager.setTorchMode(it, false) }
                if (!startSleep(duration)) {
                    cameraId?.let { manager.setTorchMode(it, false) }
                    return
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            cameraId?.let { manager.setTorchMode(it, false) }
        } catch (e: InterruptedException) {
            cameraId?.let { manager.setTorchMode(it, false) }
            throw RuntimeException(e)
        }
        cameraId?.let { manager.setTorchMode(it, false) }
    }

    private fun vibrate() {
        vibrator = (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)!!
        try {
            if (delay == 0) {
                vibrator?.vibrate(duration.toLong())
            } else {
                for (i in 0..durationSum / (duration + delay)) {
                    vibrator?.vibrate(duration.toLong())
                    if (!startSleep(duration + delay)) {
                        return
                    }

                }
            }
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    private fun startSleep(i: Int): Boolean {
        for (j in 0 until i / 50) {
            sleep(50)
            if (mode == FLASH_MODE) {
                if (!isRunningFlash) {
                    return false
                }
            } else {
                if (!isRunningVibrate) {
                    return false
                }
            }

        }
        return true
    }
}