package com.example.demo

import com.example.demo.Services.BookService
import com.example.demo.models.compleateBook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat
import java.util.*

@Controller
@CacheConfig(cacheNames = arrayOf("Books"))
class Controller(
    @Autowired
    val service:BookService
) {
    @GetMapping("/")
    fun frontend(): ResponseEntity<MutableList<compleateBook>> {
        return ResponseEntity.ok(service.get_allBook())
        //return ResponseEntity.ok(this.book_repo.findAll())
    }

    //List of Authors
    @GetMapping("author")
    fun authername(
        @RequestParam("page",defaultValue = "1") pageNo: Int
    ): ResponseEntity<List<String>> {
        var perPage=3
        return ResponseEntity.ok(service.author_name(pageNo,perPage))
        //return ResponseEntity.ok(this.book_repo.list_author(PageRequest.of(pageNo-1,perPage)))
    }

    //List of books by a particular Autho
    @GetMapping("author/books")
    fun booksbyauth(
        @RequestParam("s",defaultValue = "") s:String,
        @RequestParam("page",defaultValue = "1") pageNo: Int
    ): ResponseEntity<List<String>> {
        var perPage=1
        return ResponseEntity.ok(service.book_by_auth(s,pageNo,perPage))
    }

    //API to do textual search in book_name which can further be filtered by
    //author or categories
    @GetMapping("books/name/author_or_categ")
    fun bookname(
        @RequestParam("s1", defaultValue = "") s1:String,
        @RequestParam("s2", defaultValue = "") s2:String
    ): ResponseEntity<List<compleateBook>> {
        //return ResponseEntity.ok(this.book_repo.searc_book_name(s1,s2,s3))
        return ResponseEntity.ok(service.book_name(s1,s2))
    }

    //------POST--------//
    @PostMapping("books/add")
    fun addbook(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("name") name:String,
        @RequestParam("type") type:Array<String>,
        @RequestParam("author") author:String,

        ): ResponseEntity<compleateBook>{
        //println(new_book.title)
        //return ResponseEntity.ok(this.book_repo.save(new_book))
        var new_book=compleateBook()
        new_book.name=name
        new_book.type=type
        new_book.author=author
        new_book.book_content=file.getBytes()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        new_book.added_on=currentDate.toString()

        return ResponseEntity.ok(service.add_book(new_book))

    }


    //-------DELETE-------//
    @DeleteMapping("books/delete")
    fun delete(
        @RequestParam("name",defaultValue = "") bookname:String
    ): ResponseEntity<compleateBook> {
        //ResponseEntity.ok(this.book_repo.delete(this.book_repo.find_book(bookname)))
        //return ResponseEntity.ok(deleted_book)
        return ResponseEntity.ok(service.delete_book(bookname))
    }
}