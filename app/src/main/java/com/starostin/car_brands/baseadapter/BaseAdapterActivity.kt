package com.starostin.car_brands.baseadapter

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.starostin.car_brands.databinding.ActivityListViewBinding
import com.starostin.car_brands.databinding.DialogAddCharacterBinding
import com.starostin.car_brands.R
import kotlin.random.Random

class BaseAdapterActivity :     AppCompatActivity() {
    private lateinit var binding: ActivityListViewBinding

    private val data = mutableListOf(
        Character(id = Random.nextLong(), name = "henre", isCustom = false),
        Character(id = Random.nextLong(), name = "Anton", isCustom = false),
        Character(id = Random.nextLong(), name = "Roman", isCustom = false)
    )

    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapter(data) {
            deleteCharacter(it)
        }
        binding.spiner.adapter = adapter

        binding.spiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val character = data[position]
                binding.characterInfoTextView.text =
                    getString(R.string.character_info, character.name, character.id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.characterInfoTextView.text = ""
            }
        }
    }

    private fun onAddPressed(){
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create character")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") {d, which ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()){
                    createCharacter(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String){
        val character = Character(
            id = Random.nextLong(),
            name = name,
            isCustom = true
        )
        data.add(character)
        adapter.notifyDataSetChanged()
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE){
                data.remove(character)
                adapter.notifyDataSetChanged()
            }
        }
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Delete character")
            .setMessage("Are you sure you want to delete the character ${character.name}")
            .setPositiveButton( "Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }
}