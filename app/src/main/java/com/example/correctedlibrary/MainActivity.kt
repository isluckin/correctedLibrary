package com.example.correctedlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.correctedlibrary.databinding.ActivityMainBinding
class MainActivity : ComponentActivity() {
    private val itemAdapter = ItemAdapter()
    private  val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding.recyclerView)
        {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }

        itemAdapter.setNewData(createLibrary())
        }
    }

private fun createLibrary():List<Item>
{

    val book1: Book = Book(1, "mybook1", "I", 20, true, R.drawable.book_image)
    val dvd1: Disk = Disk(2, "diisk1", "DVD", false, R.drawable.disk_image)
    val dvd2: Disk = Disk(22, "diisk2", "СD", true, R.drawable.disk_image)
    val dvd3: Disk = Disk(23, "diisk3", "DVD", true, R.drawable.disk_image)
    val news1: Newspaper = Newspaper(31, "news1", 412, "April", true, R.drawable.newspaper_image)
    val news2: Newspaper = Newspaper(32, "news2", 412, "March", true, R.drawable.newspaper_image)
    val news3: Newspaper = Newspaper(33, "news3", 412, "June", true, R.drawable.newspaper_image)
    val book2: Book = Book(12, "mybook2", "I", 20, false, R.drawable.book_image)
    val book3: Book = Book(123, "mybook3", "I", 20, true, R.drawable.book_image)

    val itemList: MutableList<Item> = mutableListOf(
        book1, book2, book3, dvd1, dvd2, dvd3, news1, news2, news3
    )
    return itemList
}