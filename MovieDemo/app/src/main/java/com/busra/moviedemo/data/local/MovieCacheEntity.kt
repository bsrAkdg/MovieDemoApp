package com.busra.moviedemo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.busra.moviedemo.util.Constant

@Entity(tableName = Constant.DATABASE_NAME)
data class MovieCacheEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double?
) {
    override fun equals(other: Any?): Boolean {
        return id == (other as MovieCacheEntity).id
                && title == other.title
                && overview == other.overview
    }
}