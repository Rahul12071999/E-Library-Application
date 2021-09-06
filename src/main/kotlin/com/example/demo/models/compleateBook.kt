package com.example.demo.models

import javax.persistence.*

@Entity
class compleateBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int=0

    @Column
    var name=""

    @Column
    lateinit var type:Array<String>

    @Column
    var author=""

    @Column
    var added_on=""

    @Column
    @Lob
    lateinit var book_content:ByteArray

}