package com.hasanali.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.hasanali.artbooktesting.model.ImageResponse
import com.hasanali.artbooktesting.roomdb.Art
import com.hasanali.artbooktesting.util.Resource

interface ArtRepositoryInterface {

    // Dao işlemleri
    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArts(): LiveData<List<Art>>

    // Retrofit işlemleri
    suspend fun searchImage(imageString: String): Resource<ImageResponse>

}