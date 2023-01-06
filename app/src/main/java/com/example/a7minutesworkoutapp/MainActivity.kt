package com.example.a7minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minutesworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //startActivity(Intent(this@MainActivity, RoomDemoActivity::class.java))

        binding?.flStart?.setOnClickListener {
            startActivity(Intent(this@MainActivity, ExerciseActivity::class.java))
        }

        binding?.clBmi?.setOnClickListener {
            startActivity(Intent(this@MainActivity, BMIActivity::class.java))
        }

        binding?.clHistory?.setOnClickListener {
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}