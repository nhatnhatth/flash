package com.kna.flashvibrate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "sound")
data class SoundItem(
    @ColumnInfo(name = "type") var type: Int? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "time_import") var timeImport: Long? = null,
    @ColumnInfo(name = "image") var image: Int? = null,
    @ColumnInfo(name = "sound_path") var soundPath: String? = null
): Serializable {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long? = null
}

