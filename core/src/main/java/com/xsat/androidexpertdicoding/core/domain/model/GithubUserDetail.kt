package com.xsat.androidexpertdicoding.core.domain.model
 
 data class GithubUserDetail(
     val login: String,
     val id: Int,
     val avatarUrl: String,
     val followers: Int,
     val following: Int,
     val name: String
 )