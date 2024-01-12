package com.ariqhikari.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)

    @field:ColumnInfo(name = "username")
    var username: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = "",

    @field:ColumnInfo(name = "htmlUrl")
    var htmlUrl: String = "",
)