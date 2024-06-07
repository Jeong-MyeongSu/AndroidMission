package com.wjdaudtn.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wjdaudtn.recyclerview.databinding.ActivityMainBinding
import com.wjdaudtn.recyclerview.databinding.ItemMainBinding
import com.wjdaudtn.recyclerview.databinding.ItemSubBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    data class TextandType(val text: String, val type: Int)

    lateinit var data: MutableList<TextandType>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var random = (1..2)
                data.add(TextandType(query.toString(), random.random()))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initView() {
        data = mutableListOf(
            TextandType("MainItem1", 1),
            TextandType("MainItem2", 1),
            TextandType("MainItem3", 1),
            TextandType("SubItem1", 2),
            TextandType("MainItem4", 1),
            TextandType("MainItem5", 1),
            TextandType("SubItem2", 2)
        )

        binding.recyclerView.adapter = MyAdapter(data)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    class MyAdapter(var data: MutableList<TextandType>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return when (viewType) {
                1 -> MyViewHolder1(
                    ItemMainBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

                else -> MyViewHolder2(
                    ItemSubBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            when (holder) {
                is MyViewHolder1 -> holder.binding.itemMainData.text = data[position].text
                is MyViewHolder2 -> holder.binding.itemSubData.text = data[position].text
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun getItemViewType(position: Int): Int {
            return data[position].type
        }
    }

    class MyViewHolder1(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)
    class MyViewHolder2(val binding: ItemSubBinding) : RecyclerView.ViewHolder(binding.root)

}