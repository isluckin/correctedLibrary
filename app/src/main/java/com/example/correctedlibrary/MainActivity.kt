package com.example.correctedlibrary

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.correctedlibrary.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private val itemAdapter = ItemAdapter()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                with(SecondActivity)
                {
                    result.data?.let { data ->
                        val isNew = data.getBooleanExtra(IS_NEW, true)
                        if (isNew) {
                            val itemType =
                                data.getSerializableExtra(ITEM_TYPE)
                            val itemName = data.getStringExtra(ITEM_NAME)
                            val itemId = data.getIntExtra(ITEM_ID, -1)
                            data.getIntExtra(ITEM_IMG_RES, R.drawable.item_image)
                            val itemAvailable =
                                data.getBooleanExtra(ITEM_AVAILABLE, false)
                            val newItem = when (itemType) {
                                ItemType.BOOK -> Book(
                                    itemId,
                                    itemName ?: "Unknown",
                                    data.getStringExtra("AUTHOR") ?: "Unknown",
                                    data.getIntExtra("PAGES", 0),
                                    itemAvailable,
                                    R.drawable.book_image
                                )

                                ItemType.NEWSPAPER -> Newspaper(
                                    itemId,
                                    itemName ?: "Unknown",
                                    data.getIntExtra("NUMBER", 0),
                                    data.getStringExtra("MONTH") ?: "Unknown",
                                    itemAvailable,
                                    R.drawable.newspaper_image
                                )

                                ItemType.DISK -> Disk(
                                    itemId,
                                    itemName ?: "Unknown",
                                    data.getStringExtra("DISK_TYPE") ?: "Unknown",
                                    itemAvailable,
                                    R.drawable.disk_image
                                )

                                else -> Item(
                                    itemId,
                                    itemName.toString(),
                                    itemAvailable,
                                    R.drawable.item_image,
                                    type = ItemType.ITEM
                                )
                            }
                            viewModel.updateLibrary(listOf(newItem))
                        }


                    }
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewModel()
        itemAdapter.setOnClickListener { pos ->
            val item = viewModel.itemList.value?.get(pos)
            item?.let {
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra(SecondActivity.ITEM_NAME, it.itemName)
                    putExtra(SecondActivity.ITEM_ID, it.itemId)
                    putExtra(SecondActivity.ITEM_AVAILABLE, it.isAvailable)
                    putExtra(SecondActivity.ITEM_IMG_RES, it.imageRes)
                    putExtra(SecondActivity.ITEM_TYPE, it.type)
                    Log.d("CLICK LISTENER", "${it.type}")
                    when (it) {
                        is Book -> {
                            putExtra("AUTHOR", it.bookAuthor)
                            putExtra("PAGES", it.bookPages)
                        }

                        is Newspaper -> {
                            putExtra("NUMBER", it.newspaperNumber)
                            putExtra("MONTH", it.month)
                        }

                        is Disk -> {
                            putExtra("DISK_TYPE", it.diskType)
                        }
                    }
                }
                startForResult.launch(intent)
            }
        }
        with(binding.recyclerView)
        {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }
        viewModel.updateLibrary(createLibrary())

        binding.addButton.setOnClickListener()
        {

            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            showDialog()
        }


    }

    private fun showDialog() {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.menu, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroup)
        val dialog = AlertDialog.Builder(this).setTitle("Add new Item")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                when (radioGroup.checkedRadioButtonId) {
                    R.id.addBook -> {
                        startForResult.launch(SecondActivity.createIntent(this, ItemType.BOOK))
                    }

                    R.id.addNews -> startForResult.launch(
                        SecondActivity.createIntent(
                            this,
                            ItemType.NEWSPAPER
                        )
                    )

                    R.id.addDisk -> startForResult.launch(
                        SecondActivity.createIntent(
                            this,
                            ItemType.DISK
                        )
                    )
                }
            }
            .setNegativeButton("Cansel") { dialog, _ ->
                dialog.dismiss()
            }.create()
        dialog.show()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.itemList.observe(this) { item ->
            itemAdapter.setNewData(item)
        }
    }

}


private fun createLibrary(): List<Item> {

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