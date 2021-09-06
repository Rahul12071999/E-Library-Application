package com.example.demo.repositories

import com.example.demo.models.compleateBook
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface Repository: JpaRepository<compleateBook,Int> {
    //List of Authors
    @Query("select distinct author from compleate_book",nativeQuery = true)
    fun list_author(pageable: PageRequest):List<String>

    //List of books by a particular Autho
    @Query("select name from compleate_book where author=?1",nativeQuery = true)
    fun books_by_perticuler_author(s:String,pageable: PageRequest):List<String>

    //API to do textual search in book_name which can further be filtered by
    //author or categories
    @Query("select * from compleate_book where name = ?1 and author=?2",nativeQuery = true)
    fun searc_book_name(s:String,s1:String):List<compleateBook>

    @Query("select * from compleate_book where author = ?1",nativeQuery = true)
    fun search(s:String):List<compleateBook>

    //find book by name
    @Query("select * from compleate_book where name = ?1",nativeQuery = true)
    fun find_book(s:String): compleateBook
}