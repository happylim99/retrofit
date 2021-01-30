package com.sean.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService : AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

//        getRequestWithQueryParameters()
//        getRequestWithPathParameters()
        uploadAlbum()

    }

    private fun getRequestWithQueryParameters() {
        val responseLiveData:LiveData<Response<Album>> = liveData {
            val response = retService.getAlbums()
//            val response = retService.getAlbumByUserId(3)
//            val response = retService.getAlbumsById(3)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if(albumList!=null) {
                while(albumList.hasNext()) {
                    val albumItem = albumList.next()
                    val result:String = " " + "Album title : ${albumItem.title}" + "\n" +
                            " " + "Album id : ${albumItem.id}" + "\n" +
                            " " + "User id : ${albumItem.userId}" + "\n\n\n"
                    textView.append(result)
                }

            }
        })
    }

    private fun getRequestWithPathParameters() {
        val pathResponse : LiveData<Response<AlbumItem>> = liveData {
            val response = retService.getAlbumsById(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
        })

    }

    private fun uploadAlbum() {
        val album = AlbumItem(0, "My title", 3)
        val postResponse: LiveData<Response<AlbumItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {
            val receivedAlbumsItem = it.body()
            val result = " " + "Album Title : ${receivedAlbumsItem?.title}" + "\n" +
                    " " + "Album id : ${receivedAlbumsItem?.id}" + "\n" +
                    " " + "User id : ${receivedAlbumsItem?.userId}" + "\n\n\n"
            textView.text = result
        })
    }
}
