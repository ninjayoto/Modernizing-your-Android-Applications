package com.packt.coolnewandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.packt.coolnewandroid.databinding.ActivityMainBinding
import com.packt.coolnewandroid.room.CoolNewDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myViewModel: AutoViewModel

//    private var recyclerAdapter: AutomobileRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)


//        for (i in 0..35) {
//            Thread {
//                CoolNewDatabase.getAppDataBase(applicationContext)!!.dao().insertAuto(Automobile("make #$i", "model #$i"))
//            }.start()
//        }

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

//        recyclerAdapter = AutomobileRecyclerAdapter(this, lifecycle)
//        lifecycle.addObserver(recyclerAdapter!!)
//        recyclerView.adapter = recyclerAdapter


        myViewModel = ViewModelProviders.of(this).get(AutoViewModel(application as CoolApplication)::class.java)
        binding.viewModel = myViewModel
        binding.setLifecycleOwner(this)




        recyclerView.setHasFixedSize(true)
        val pagedAdapter = CoolPagedListAdapter(this)
        recyclerView.adapter = pagedAdapter
//        myViewModel.autos.observe(this, )



        textView.setOnClickListener {
            Thread {
                CoolNewDatabase.getAppDataBase(applicationContext)!!.dao().deleteAll()
            }.start()
        }


        val autosObserver = Observer<PagedList<Automobile>> { autos ->
//            recyclerAdapter!!.setAutomobiles(autos as ArrayList<Automobile>)
            pagedAdapter.submitList(autos)
        }

        myViewModel.autos!!.observe(this, autosObserver)
    }


    fun onClick(view: View) {
        val intent = Intent(this@MainActivity, FormActivity::class.java)
        startActivity(intent)
    }
}

fun Context.toast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

fun log(string: String) {
    Log.d("MainActivity", string)
}