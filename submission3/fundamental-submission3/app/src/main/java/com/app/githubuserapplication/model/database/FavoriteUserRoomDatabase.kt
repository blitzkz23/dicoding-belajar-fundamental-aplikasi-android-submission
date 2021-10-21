package com.app.githubuserapplication.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {

}