package com.example.demo

import com.example.demo.models.compleateBook
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest
@AutoConfigureMockMvc
internal class ControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    var mock_Mvc:MockMvc
){

    @DisplayName("GET /")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllBooks {
        @Test
        fun get_all_books() {
            // when/then
            mockMvc.get("/")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0].id") { value(8) }
                    jsonPath("$[1].id") { value(9) }
                    jsonPath("$[2].id") { value(10) }
                    jsonPath("$[3].id") { value(11) }
                    jsonPath("$[4].id") { value(12) }
                }
        }
    }

    @DisplayName("GET /author")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllAuthors {
        @Test
        fun get_all_authors() {
            // given
            // when/then
            mockMvc.get("/author")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0]") { value("Andy Weir") }
                }
        }
    }

    @DisplayName("GET /author/books")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class BooksByAuthor {
        @Test
        fun get_books_by_author() {
            // given
            // when/then
            var auth="Andy Weir"
            mockMvc.get("/author/books?s=$auth")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0]") { value("Project Hail Mary") }
                }
        }
    }
    @DisplayName("GET /books/name/author_or_categ")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class BooksByNameAuthor {
        @Test
        fun get_books_by_name_author() {
            // given
            // when/then
            var author_name="Andy Weir"
            var book_name="Project Hail Mary"
            mockMvc.get("/books/name/author_or_categ?s1=$book_name&s2=$author_name")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0].id") { value(8) }
                    jsonPath("$[0].name") { value("Project Hail Mary") }
                    jsonPath("$[0].author") { value("Andy Weir") }
                }
        }
    }



    @Nested
    @DisplayName("POST /books/add")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBook {
        @Autowired
        private val webApplicationContext: WebApplicationContext? = null
        @Test
        fun add_book() {
            // given
            var new_book= compleateBook()
            new_book.name="The Push: A Novel"
            new_book.type= arrayOf<String>("Thriller")
            new_book.author="ASHLEY AUDRAIN"
            new_book.book_content=ByteArray(0)

            val file = MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello World!".toByteArray()
            )

            mock_Mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!).build()
            mock_Mvc.perform(multipart("/books/add")
                .file(file)
                .param("name","The Push: A Novel")
                .param("type","Thriller")
                .param("author","ASHLEY AUDRAIN")
            ).andExpect { HttpStatus.OK}

        }
    }

    @Nested
    @DisplayName("DELETE /books/delete")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBook {
        @Autowired
        private val webApplicationContext: WebApplicationContext? = null
        @Test
        fun add_book() {
            // given
            var new_book= compleateBook()
            new_book.name="A Novel"
            new_book.type= arrayOf<String>("Thriller")
            new_book.author="ASHLEY AUDRAIN"
            new_book.book_content=ByteArray(0)

            val file = MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello World!".toByteArray()
            )

            mock_Mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!).build()
            mock_Mvc.perform(multipart("/books/add")
                .file(file)
                .param("name","A Novel")
                .param("type","Thriller")
                .param("author","ASHLEY AUDRAIN")
            ).andExpect { HttpStatus.OK}

        }
        @Test
        @DirtiesContext
        fun DeleteBookbyName() {
            // given
            val book_name = "The Push: A Novel"
            // when/then
            mock_Mvc.delete("/books/delete?name=$book_name")
                .andDo { print() }
                .andExpect {
                    status { HttpStatus.OK }
                }
        }
    }


}