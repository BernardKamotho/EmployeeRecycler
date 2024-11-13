package com.example.employeeapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray

class ViewEmployees : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_employees)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } //end insets

        //find the progressbar by use of its id
        val progress = findViewById<ProgressBar>(R.id.progress)

        //specify the url to get the employees
        val api = "https://kimaniben.pythonanywhere.com/RetrieveEmployees"

        //access the helper
        val helper = ApiHelper(applicationContext)

        //inside of the helper class, access the get function
        helper.get(api, object : ApiHelper.CallBack {
            //convert the get results into a json array
            override fun onSuccess(result: String?) {
                val employeeJsonArray = JSONArray(result.toString())

                //load the results to the textView
                val empdata = findViewById<TextView>(R.id.empdata)

                //a single employee
                (0 until employeeJsonArray.length()).forEach {
                    val employee = employeeJsonArray.getJSONObject(it)
                    empdata.append("ID Number:  " + employee.get("id_number") + "\n")
                    empdata.append("Fisrt Name: " + employee.get("username") + "\n")
                    empdata.append("Last Name:  " + employee.get("others") + "\n")
                    empdata.append("salary:     " + employee.get("salary") + "\n")
                    empdata.append("Department: " + employee.get("department") + "\n")
                    empdata.append("\n \n")
                }
                //stop the progressbar after a successfull fetch of data
                progress.visibility = View.GONE




            }

        })
    }
}