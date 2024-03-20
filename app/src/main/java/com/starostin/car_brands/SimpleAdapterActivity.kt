package com.starostin.car_brands

import androidx.appcompat.app.AppCompatActivity
import android.widget.SimpleAdapter
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import com.starostin.car_brands.databinding.ActivitySimpleListViewBinding

class SimpleAdapterActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySimpleListViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListViewWithSimpleGeneratedData()
    }

    private fun setupListViewWithSimpleGeneratedData(){
        val data = (1..100).map {
            mapOf(
                KEY_TITLE to "Item #$it",
                KEY_DESCRIPTION to "Description #$it"
            )
        }

        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.item_custom,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(R.id.titleTextView, R.id.descriptionTextView)
        )
        binding.listView.adapter = adapter
    }

    private fun setupListViewSimple() {
        val data = listOf(
            mapOf(
                KEY_TITLE to "Заголовок 1-го элемента",
                KEY_DESCRIPTION to "Содержимое для первого элемента"
            ),
            mapOf(
                KEY_TITLE to "Заголовок 2-го элемента",
                KEY_DESCRIPTION to "Содержимое для второго элемента"
            ),
            mapOf(
                KEY_TITLE to "Заголовок 3-го элемента",
                KEY_DESCRIPTION to "Содержимое для третьего элемента"
            )
        )

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        binding.listView.adapter = adapter

        binding.listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemTitle = data[position][KEY_TITLE]
                val selectedItemDescriptor = data[position][KEY_DESCRIPTION]

                val dialog = AlertDialog.Builder(this)
                    .setTitle(selectedItemTitle)
                    .setMessage(getString(R.string.item_selected_message, selectedItemDescriptor))
                    .setPositiveButton("ok") {dialog, whis ->}
                    .create()
                dialog.show()
            }
    }

    companion object {
        @JvmStatic
        val KEY_TITLE = "title"

        @JvmStatic
        val KEY_DESCRIPTION = "description"
    }
}
