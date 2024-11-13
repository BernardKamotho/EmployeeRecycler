package com.example.employeeapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class RegisterEmployee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_employee)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } //end insets

        //find all the 5 editTexts and one button by use of their ids

        val id_number = findViewById<EditText>(R.id.id_number)
        val username = findViewById<EditText>(R.id.username)
        val others = findViewById(R.id.others)  as EditText
        val salary = findViewById<EditText>(R.id.salary)
        val department = findViewById<EditText>(R.id.department)
        val savebtn = findViewById<Button>(R.id.saveBtn)

        //add a click listener to our button
        savebtn.setOnClickListener {
            //create a json object that will hold data gotten from the five edittexts.
            // The data is already in the variables creared above
            val body = JSONObject()

            //add idnumber, username, others, salary and department to the json object
            body.put("id_number", id_number.text.toString())
            body.put("username", username.text.toString())
            body.put("others", others.text.toString())
            body.put("salary", salary.text.toString())
            body.put("department", department.text.toString())

            //specify the api endpoint for creating an employee
            val api = "https://kimaniben.pythonanywhere.com/createEmployee"

            //Access the api helper class
            //Trigger the post function inside of the api helper class.
            //Provide the data requires by the two parameters

            val helper = ApiHelper(applicationContext)

            //access the post function
            helper.post(api,body)
        }

    }
}