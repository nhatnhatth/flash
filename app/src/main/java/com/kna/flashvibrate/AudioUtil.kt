package com.kna.flashvibrate

import android.content.Context
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import linc.com.library.AudioTool
import java.io.File
import java.time.Duration


object AudioUtil {
    fun cutAudio( context: Context, start: Int, duration: Int, path: String, callback: (outputPath: String) -> Unit) {
        var outputPath = File(path).parent + "/clone_audio3.mp3"
        AudioTool.getInstance(context)
            .withAudio(File(path))
            .cutAudio(start,duration) { callback.invoke(outputPath) }
            .saveCurrentTo(outputPath)
            .release()
    }
}