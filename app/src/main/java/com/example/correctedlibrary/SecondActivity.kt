package com.example.correctedlibrary

import android.content.Context
import android.content.Intent
import android.media.RouteListingPreference
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import com.example.correctedlibrary.databinding.ActiviryTwoBinding
import kotlin.math.log

class SecondActivity : ComponentActivity() {
    private val binding by lazy { ActiviryTwoBinding.inflate(layoutInflater) }
    private  var existingName: String? = null
    private var existingId: Int = 0
    private var existingAvailable: Boolean = false
    private var existingImgRes: Int? = R.drawable.item_image
    private var existingItemType: ItemType = ItemType.ITEM
    private var isNew: Boolean= true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        existingName = intent.getStringExtra(ITEM_NAME)?: "Unknown"
        existingId = intent.getIntExtra(ITEM_ID, -1)
        existingAvailable = intent.getBooleanExtra(ITEM_AVAILABLE, true)
        existingImgRes = intent.getIntExtra(ITEM_IMG_RES, R.drawable.item_image)
        existingItemType = intent.getSerializableExtra(ITEM_TYPE) as ItemType
        isNew = !intent.hasExtra(ITEM_NAME)


        with(binding) {
            existingName?.let { bigItemName.setText(it) }
            bigItemID.setText(existingId.toString())
            bigItemIsAvailable.setText(if (existingAvailable) "It available" else "It don't available")
            existingImgRes?.let { bigItemImg.setImageResource(it) }
        }

        if (existingItemType == ItemType.BOOK) {
            val author = intent.getStringExtra("AUTHOR")  ?: "Unknown"
            val pages = intent.getIntExtra("PAGES", 0)
            binding.bookAuthor.setText("Author: $author")
            binding.bookPages.setText("Pages: $pages")
        }
        else if (existingItemType == ItemType.NEWSPAPER) {
            val number = intent.getIntExtra("NUMBER", 0)
            val month = intent.getStringExtra("MONTH")  ?: "Unknown"
            binding.newspaperNumber.setText("Number: $number")
            binding.newspaperMonth.setText("Month: $month")
        }
        else if (existingItemType == ItemType.DISK) {
            val diskType = intent.getStringExtra("DISK_TYPE")  ?: "Unknown"
            binding.diskType.setText("Type: $diskType")
        }

        setupUIForItemType(existingItemType)

        binding.applyBtn.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra(IS_NEW, isNew)
                putExtra(ITEM_NAME, binding.bigItemName.text.toString())
                putExtra(ITEM_ID, binding.bigItemID.text.toString().toIntOrNull() ?: 0)
                putExtra(ITEM_AVAILABLE, existingAvailable)
                putExtra(ITEM_IMG_RES, existingImgRes ?: R.drawable.item_image)
                putExtra(ITEM_TYPE, existingItemType)

                if (existingItemType == ItemType.BOOK) {
                    putExtra("AUTHOR", binding.bookAuthor.text.toString())
                    putExtra("PAGES", binding.bookPages.text.toString().toIntOrNull() ?: 0)

                }
                else if (existingItemType == ItemType.NEWSPAPER) {
                    putExtra("NUMBER", binding.newspaperNumber.text.toString().toIntOrNull() ?: 0)
                    putExtra("MONTH", binding.newspaperMonth.text.toString())
                }
                else if (existingItemType == ItemType.DISK) {
                    putExtra("DISK_TYPE", binding.diskType.text.toString())
                }
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun setupUIForItemType(itemType: ItemType) {
        with(binding) {
            bookAuthor.visibility = View.GONE
            bookPages.visibility = View.GONE
            newspaperNumber.visibility = View.GONE
            newspaperMonth.visibility = View.GONE
            diskType.visibility = View.GONE
            Log.d("FUN setup", "$itemType")
            if (itemType == ItemType.BOOK) {
                bookAuthor.visibility = View.VISIBLE
                bookPages.visibility = View.VISIBLE
                bookAuthor.isEnabled = isNew
                bookPages.isEnabled = isNew
                Log.d("BOOK", "$itemType")
            }
            else if (itemType == ItemType.NEWSPAPER) {
                newspaperNumber.visibility = View.VISIBLE
                newspaperMonth.visibility = View.VISIBLE
                newspaperNumber.isEnabled = isNew
                newspaperMonth.isEnabled = isNew
                Log.d("NEWS", "$itemType")
            }
            else if (itemType == ItemType.DISK) {
                diskType.visibility = View.VISIBLE
                diskType.isEnabled = isNew
                Log.d("DISK", "$itemType")
            }
            bigItemName.isEnabled = isNew
            bigItemID.isEnabled = isNew
        }
    }

    companion object{
        fun createIntent(context: Context, itemType: ItemType): Intent {
            return Intent(context, SecondActivity::class.java).apply {
                putExtra(ITEM_TYPE, itemType)
            }
        }
        const val ITEM_TYPE: String = "item type"
        const  val ITEM_NAME: String = "item name"
        const val ITEM_ID: String = "item ID"
        const val ITEM_AVAILABLE: String = "item available"
        const val ITEM_IMG_RES: String = "item img res"
        const val IS_NEW: String = "new item"
    }

}
enum class ItemType
{
  ITEM,  BOOK, NEWSPAPER, DISK
}

