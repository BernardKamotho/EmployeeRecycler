package com.example.employeeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.Adapters.EmployeeAdapter
import com.example.employeeapp.Models.Employee
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class Get2 : AppCompatActivity() {
    lateinit var itemList: List<Employee>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } //end insets

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        val EmployeeAdapter = EmployeeAdapter(applicationContext)
        val layManager = LinearLayoutManager(applicationContext)

        recyclerView.adapter = EmployeeAdapter
        recyclerView.layoutManager = layManager

        fun fetchData(){
            //Go to the PAi get the dataapplicationContextthis
            val api = "https://kimaniben.pythonanywhere.com/RetrieveEmployees"
            val helper = ApiHelper(applicationContext)
            helper.get(api, object: ApiHelper.CallBack{

                override fun onSuccess(result: String?) {
                    //Take above result to adapter
                    //Convert Above result from JSON array to LIST<Lab>
                    val gson = GsonBuilder().create()
                    itemList = gson.fromJson(result.toString(),
                        Array<Employee>::class.java).toList()
                    //Finally, our adapter has the data
                    EmployeeAdapter.setListItems(itemList)
                    //For the sake of recycling/Looping items, add the adapter to recycler
                    recyclerView.adapter = EmployeeAdapter
                }

            })
        }//end fetch data

        fetchData()



    }
}
