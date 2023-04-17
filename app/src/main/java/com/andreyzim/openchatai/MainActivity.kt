package com.andreyzim.openchatai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreyzim.openchatai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mAdapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val mLayoutManager = LinearLayoutManager(baseContext)

        binding.chatLayout.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }
}