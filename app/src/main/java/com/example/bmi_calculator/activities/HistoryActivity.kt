package com.example.bmi_calculator.activities

import HistoryAdapter
import HistoryViewModel
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi_calculator.R


class HistoryActivity : AppCompatActivity() {
    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val recyclerView = findViewById<RecyclerView>(R.id.historyRecyclerView)
        val adapter = HistoryAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        historyViewModel.loadHistoryFromSharedPreferences(this)
        // Obserwuj historię i uaktualniaj widok przy zmianach
        historyViewModel.historyList.observe(this) { history ->
            adapter.submitList(history)
        }
    }
}
