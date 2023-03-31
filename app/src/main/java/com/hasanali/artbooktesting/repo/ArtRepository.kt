package com.hasanali.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.hasanali.artbooktesting.api.RetrofitAPI
import com.hasanali.artbooktesting.model.ImageResponse
import com.hasanali.artbooktesting.roomdb.Art
import com.hasanali.artbooktesting.roomdb.ArtDao
import com.hasanali.artbooktesting.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI): ArtRepositoryInterface{

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArts(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Null",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            Resource.error("No data!",null)
        }
    }
}