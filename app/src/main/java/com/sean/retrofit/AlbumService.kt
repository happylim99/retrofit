package com.sean.retrofit

import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums(): Response<Album>

    @GET("/albums")
    suspend fun getAlbumByUserId(@Query("userId") userId:Int): Response<Album>

    @GET("/albums/{id}")
    suspend fun getAlbumsById(@Path(value = "id") albumId:Int): Response<AlbumItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body album: AlbumItem) : Response<AlbumItem>
}