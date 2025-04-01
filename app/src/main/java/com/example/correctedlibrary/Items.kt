package com.example.correctedlibrary

open class Item(
    val itemId: Int,
    val itemName: String,
    var isAvailable: Boolean,
    val imageRes: Int?
) {
    fun shortInfo() {
        println("$itemName available: ${if (isAvailable) "Yes" else "No"}")
    }

    open fun fullInfo() {
        println(
            """
            id: $itemId
            name: $itemName
            available: ${if (isAvailable) "Yes" else "No"}
        """.trimIndent()
        )
    }

    fun returnToLibrary() {
        if (!isAvailable) {
            isAvailable = true
            println("$itemName successfully returned")
        } else {
            println("$itemName cannot be returned")
        }
    }
}

class Book(
    bookId: Int,
    bookName: String,
    private val bookAuthor: String,
    private val bookPages: Int,
    isAvailable: Boolean,
    bookImage: Int?
) : Item(bookId, bookName, isAvailable,bookImage ), Takeable, Readable {
    override fun fullInfo() {
        println("Book: $itemName ($bookPages pages) by $bookAuthor with ID: $itemId, available: ${if (isAvailable) "Yes" else "No"}")
    }

    override fun takeToHome() {
        if (isAvailable) {
            isAvailable = false
            println("Book $itemId has been taken home")
        } else {
            println("Book $itemId cannot be taken home")
        }
    }

    override fun readInHall() {
        if (isAvailable) {
            isAvailable = false
            println("Book $itemId has been taken to the reading hall")
        } else {
            println("Book $itemId cannot be taken to the reading hall")
        }
    }
}

class Newspaper(
    private val newspaperId: Int,
    private val newspaperName: String,
    private val newspaperNumber: Int,
    private val month: String,
    isAvailable: Boolean,
    newspaperImage: Int?
) : Item(newspaperId, newspaperName,isAvailable, newspaperImage), Readable {
    override fun fullInfo() {
        println("Release $newspaperNumber of newspaper $itemName from $month with ID: $itemId, available: ${if (isAvailable) "Yes" else "No"}")
    }

    override fun readInHall() {
        if (isAvailable) {
            isAvailable = false
            println("Newspaper $itemId has been taken to the reading hall")
        } else {
            println("Newspaper $itemId cannot be taken to the reading hall")
        }
    }
}

class Disk(
    diskId: Int,
    diskName: String,
    private val diskType: String,
    isAvailable: Boolean,
    diskImage: Int?
) : Item(diskId, diskName, isAvailable, diskImage), Takeable {
    override fun fullInfo() {
        println("$diskType $itemName, available: ${if (isAvailable) "Yes" else "No"}")
    }

    override fun takeToHome() {
        if (isAvailable) {
            isAvailable = false
            println("Disk $itemId has been taken home")
        } else {
            println("Disk $itemId cannot be taken home")
        }
    }
}

interface Takeable {
    fun takeToHome()
}

interface Readable {
    fun readInHall()
}

