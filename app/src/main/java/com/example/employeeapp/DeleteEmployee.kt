package com.example.employeeapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class DeleteEmployee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete_employee)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } //end insets


        val id_number = findViewById<EditText>(R.id.id_number)
        val deletebtn = findViewById<Button>(R.id.deleteBtn)

        //add a click listener to our button
        deletebtn.setOnClickListener {
            //create a json object that will hold data gotten from the five edittexts.
            // The data is already in the variables creared above
            val body = JSONObject()

            //add idnumber, username, others, salary and department to the json object
            body.put("id_number", id_number.text.toString())

            //specify the api endpoint for creating an employee
            val api = "https://kimaniben.pythonanywhere.com/RemoveEmployee"

            //Access the api helper class
            //Trigger the post function inside of the api helper class.
            //Provide the data requires by the two parameters

            val helper = ApiHelper(applicationContext)

            //access the post function
            helper.post(api,body)
        }
    }
}