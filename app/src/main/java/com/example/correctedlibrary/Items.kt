package com.example.correctedlibrary

open class Item(
    val itemId: Int,
    val itemName: String,
    var isAvailable: Boolean?,
    val imageRes: Int?,
    val type: ItemType
)


class Book(
    bookId: Int,
    bookName: String,
    val bookAuthor: String,
    val bookPages: Int?,
    isAvailable: Boolean?,
    bookImage: Int?,
    val bookType: ItemType = ItemType.BOOK


) : Item(bookId, bookName, isAvailable, bookImage, bookType), Takeable, Readable {
    override fun takeToHome() {

    }

    override fun readInHall() {
    }

}

class Newspaper(
    val newspaperId: Int,
    val newspaperName: String,
    val newspaperNumber: Int?,
    val month: String,
    isAvailable: Boolean,
    newspaperImage: Int?,
    val newsType: ItemType = ItemType.NEWSPAPER
) : Item(newspaperId, newspaperName, isAvailable, newspaperImage, newsType), Readable {
    override fun readInHall() {
    }
}

class Disk(
    diskId: Int,
    diskName: String,
    val diskType: String,
    isAvailable: Boolean,
    diskImage: Int?,
    disktype: ItemType = ItemType.DISK

) : Item(diskId, diskName, isAvailable, diskImage, disktype), Takeable {
    override fun takeToHome() {
    }
}


interface Takeable {
    fun takeToHome()
}

interface Readable {
    fun readInHall()
}

