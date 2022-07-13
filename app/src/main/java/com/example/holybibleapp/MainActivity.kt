package com.example.holybibleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.holybibleapp.core.BibleApp
import com.example.holybibleapp.presentation.BibleAdapter
import com.example.holybibleapp.presentation.MainViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (application as BibleApp).mainViewModel

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = BibleAdapter(object : BibleAdapter.Retry {
            override fun tryAgain() = viewModel.fetchBooks()
        },
            object : BibleAdapter.CollapseListener {
                override fun collapseOrExpand(id: Int) = viewModel.collapseOrExpand(id)
            })
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel.observe(this) {
            adapter.update(it)
        }
        viewModel.fetchBooks()
    }

    override fun onPause() {
        viewModel.saveCollapsedStates()
        super.onPause()
    }
}