package com.example.demo.Services

import com.example.demo.models.compleateBook
import com.example.demo.repositories.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BookService(//private val book_repo:BookRepository)
    @Autowired
    private val book_repository:Repository
) {
    //frontend

    fun get_allBook(): MutableList<compleateBook> {
        return this.book_repository.findAll()
    }

    //authername
    fun author_name(pageNo:Int,perPage:Int): List<String> {
        return this.book_repository.list_author(PageRequest.of(pageNo-1,perPage))
    }

    //booksbyauth
    fun book_by_auth(s:String,pageNo: Int,perPage: Int): List<String> {
        return this.book_repository.books_by_perticuler_author(s,PageRequest.of(pageNo-1,perPage))
    }

    //bookname
    fun book_name(s1:String,s2:String): List<compleateBook> {
        return this.book_repository.searc_book_name(s1,s2)
    }

    //------POST--------//
    //addbook
    fun add_book(new_book:compleateBook): compleateBook {
        return this.book_repository.save(new_book)
    }

//    fun upload_book(new_file:bookContent): bookContent {
//        return this.book_content_rep.save(new_file)
//    }

    //-------DELETE-------//
    //delete
    fun delete_book(bookname:String): compleateBook {
        val deleted_book=this.book_repository.find_book(bookname)
        this.book_repository.delete(deleted_book)
        return deleted_book
    }
}