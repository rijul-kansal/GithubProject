package com.example.githubproject.Activity
import androidx.appcompat.widget.SearchView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubproject.Adapter.Adapter
import com.example.githubproject.Apis.ApiService
import com.example.githubproject.Apis.Constants
import com.example.githubproject.Model.ShownModel
import com.example.githubproject.R
import com.example.githubproject.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class MainActivity : AppCompatActivity() {
    var owner = "octocat"
    var repo = "Hello-World"
    lateinit var binding: ActivityMainBinding
    lateinit var items: ArrayList<ShownModel>
    lateinit var searchView: SearchView
    lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        items = ArrayList()
        SetUpToolbar()
        getdata()
        owner=intent.getStringExtra(Constants.OWNER).toString()
        repo=intent.getStringExtra(Constants.REPO_NAME).toString()
        // Initialize and set up the search view
        searchView = binding.idSV
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                handleSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text change
                handleSearchQuery(newText)
                return true
            }
        })
    }

    private fun handleSearchQuery(query: String?) {
        if (query != null) {
            val filteredItems = ArrayList(items.filter { it.title.contains(query, ignoreCase = true) })
            adapter.updateData(filteredItems)

            // Applying OnClickListener to the filtered Adapter
            adapter.setOnClickListener(object : Adapter.OnClickListener {
                override fun onClick(position: Int, model: ShownModel) {
                    var intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(Constants.TITLE, filteredItems[position].title)
                    intent.putExtra(Constants.CREATED_DATE, filteredItems[position].createdDate)
                    intent.putExtra(Constants.CLOSED_DATE, filteredItems[position].closedDate)
                    intent.putExtra(Constants.USERNAME, filteredItems[position].username)
                    intent.putExtra(Constants.IMAGE_URL, filteredItems[position].userImageUrl)

                    startActivity(intent)
                }
            })
        }
    }

    private fun setUpRecycleView() {
        binding.recycleview.layoutManager = LinearLayoutManager(this)
        binding.recycleview.setHasFixedSize(true)
        adapter = Adapter(items, this@MainActivity)

        // Assign ItemAdapter instance to our RecyclerView
        binding.recycleview.adapter = adapter

        // Applying OnClickListener to our Adapter
        adapter.setOnClickListener(object : Adapter.OnClickListener {
            override fun onClick(position: Int, model: ShownModel) {
                var intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(Constants.TITLE, items[position].title)
                intent.putExtra(Constants.CREATED_DATE, items[position].createdDate)
                intent.putExtra(Constants.CLOSED_DATE, items[position].closedDate)
                intent.putExtra(Constants.USERNAME, items[position].username)
                intent.putExtra(Constants.IMAGE_URL, items[position].userImageUrl)

                startActivity(intent)
            }
        })
    }
    private fun SetUpToolbar()
    {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            // displaying arrow
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setDisplayShowHomeEnabled(true)
            // custom arrow design
            // setting title for tolbar
            supportActionBar!!.title=resources.getString(R.string.app_name)
        }
        // back navigation when we click back btn present on toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getdata() {
        val matchApi = Constants.getInstance().create(ApiService::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
                val result = matchApi.getSeries(owner,repo)
                if (result.isSuccessful && result.body() != null) {
                    withContext(Dispatchers.Main) {
                        val responseData = result.body()
                        if (responseData != null) {
                            Toast.makeText(this@MainActivity, "Successfull", Toast.LENGTH_LONG).show()
                            Log.e("rijul", "Successfull")
                            for(i in 0..responseData.size-1)
                            {
                                val model= ShownModel(responseData[i].title,responseData[i].created_at,responseData[i].closed_at.toString(),responseData[i].user.login,responseData[i].user.avatar_url)
                                items.add(model)
                            }
                            Log.e("rijul", items.size.toString())
                            if(items.size>0)
                            {
                                setUpRecycleView()
                            }
                        }
                        else
                        {
                            Toast.makeText(this@MainActivity, "responce data is null", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "API request failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}