package com.example.holybibleapp

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val viewModel = (application as BibleApp).mainViewModel

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = BibleAdapter()
        recyclerView.adapter = adapter

        viewModel.observe(this, Observer {
            adapter.update(it)
        })
        viewModel.fetchBooks()
    }
}