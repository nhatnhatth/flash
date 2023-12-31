package com.kna.flashvibrate.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kna.flashvibrate.data.model.SoundItem


@Dao
interface SoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(soundItem: SoundItem)

    @Query("SELECT * FROM sound ORDER BY time_import DESC")
    fun getAllSound(): List<SoundItem>

    @Query("DELETE FROM sound WHERE sound_path =:path ")
    fun delete(path: String)
}