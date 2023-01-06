package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val historyDao = (application as WorkOutApp).db.historyDao()
        lifecycleScope.launch {
            historyDao.fetchAllEntries().collect {
                setHistoryRecyclerView(it)
            }
        }
    }

    private fun setHistoryRecyclerView(historyList: List<HistoryEntity>) {

        if (historyList.isNotEmpty()) {
            val historyItemAdapter = HistoryItemAdapter(historyList)

            binding?.rvHistory?.layoutManager = LinearLayoutManager(
                this)

            binding?.rvHistory?.adapter = historyItemAdapter

            binding?.tvHistoryTitle?.visibility = View.VISIBLE
            binding?.rvHistory?.visibility = View.VISIBLE
            binding?.tvHistoryNoRecords?.visibility = View.GONE
        } else {
            binding?.tvHistoryTitle?.visibility = View.GONE
            binding?.rvHistory?.visibility = View.GONE
            binding?.tvHistoryNoRecords?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}