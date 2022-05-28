package com.example.polycareer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.polycareer.data.database.model.DirectionEntity
import com.example.polycareer.data.database.model.ProfessionEntity

@Dao
interface ProfessionsDao {
    @Query("SELECT * FROM professions ORDER BY id")
    suspend fun getAllProfessions(): List<ProfessionEntity>

    @Query("DELETE FROM professions")
    suspend fun deleteAllProfessions()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllProfessions(directions: List<ProfessionEntity>)

    @Query("SELECT count(*) FROM coeffs GROUP BY profession ORDER BY profession")
    suspend fun getAllCountOfAnswers(): List<Int>
}
