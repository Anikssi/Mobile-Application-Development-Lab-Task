package com.university.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.university.newsapp.databinding.ActivityMainBinding
import com.university.newsapp.model.Post
import com.university.newsapp.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadPosts()

        binding.swipeRefresh.setOnRefreshListener {
            loadPosts()
        }
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter { post ->
            val intent = Intent(this, PostDetailActivity::class.java).apply {
                putExtra("POST_ID", post.id)
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }

    private fun loadPosts() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.tvError.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val posts = RetrofitClient.instance.getAllPosts()
                postAdapter.submitList(posts)
                binding.recyclerView.visibility = View.VISIBLE
            } catch (e: Exception) {
                binding.tvError.text = "Failed to load posts: ${e.message}"
                binding.tvError.visibility = View.VISIBLE
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }
}