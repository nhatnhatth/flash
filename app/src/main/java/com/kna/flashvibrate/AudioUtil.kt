package com.kna.flashvibrate

import android.content.Context
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import java.io.File


object AudioUtil {
    fun cutAudio( context: Context, start: Int, end: Int, path: String, callback: (outputPath: String) -> Unit) {
        var outputPath = File(path).parent + "/clone_audio.mp3"
        var ffmpegCommand = arrayOf<String>(
            "-ss", java.lang.String.valueOf(start),
            "-i", path,
            "-t", java.lang.String.valueOf(end - start),
            "-c", "copy",
            outputPath
        )
        FFmpeg.getInstance(context).execute(ffmpegCommand, object : ExecuteBinaryResponseHandler() {
            override fun onSuccess(message: String) {
                callback.invoke("success")

            }

            override fun onFailure(message: String) {
                callback.invoke("fail")
            }

            override fun onProgress(message: String) {
                // Trimming progress updates
            }

            override fun onStart() {
                // Trimming started
            }

            override fun onFinish() {

            }
        })
    }
}