package com.example.newsly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsly.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 2, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun articleDao(): ArticleDao
}