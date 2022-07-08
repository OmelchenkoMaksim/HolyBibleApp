package com.example.holybibleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.holybibleapp.core.BibleApp
import com.example.holybibleapp.presentation.BibleAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = (application as BibleApp).mainViewModel

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = BibleAdapter(object : BibleAdapter.Retry {
            override fun tryAgain() {
                viewModel.fetchBooks()
            }
        })
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel.observe(this) {
            adapter.update(it)
        }
        viewModel.fetchBooks()
    }
}