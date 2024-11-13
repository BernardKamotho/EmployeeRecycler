package com.example.employeeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } //end insets

        //find the four buttons by use of their ids create an explicit intent
        val registerBtn = findViewById<Button>(R.id.postEmployee)
        val getBtn = findViewById<Button>(R.id.RetrieveEmployees)
        val updateBtn = findViewById<Button>(R.id.UpdateEmployee)
        val deleteBtn = findViewById<Button>(R.id.DeleteEmployee)
        val get2btn = findViewById<Button>(R.id.get2btn)
        val upload2 = findViewById<Button>(R.id.upload2)

        //create explicit intent to the different pages
        registerBtn.setOnClickListener {
            val registerpage = Intent(applicationContext, RegisterEmployee::class.java)
            startActivity(registerpage)

            Toast.makeText(applicationContext, "Loading Register Page...", Toast.LENGTH_SHORT).show()
        } //end listener

        getBtn.setOnClickListener {
            val retrievepage = Intent(applicationContext, ViewEmployees::class.java)
            startActivity(retrievepage)

            Toast.makeText(applicationContext, "Loading Page...", Toast.LENGTH_SHORT).show()
        } //end listener

        updateBtn.setOnClickListener {
            val updateEmployeepage = Intent(applicationContext, UpdateEmployee::class.java)
            startActivity(updateEmployeepage)

            Toast.makeText(applicationContext, "Loading Update Page...", Toast.LENGTH_SHORT).show()
        } //end listener

        deleteBtn.setOnClickListener {
            val Deletepage = Intent(applicationContext, DeleteEmployee::class.java)
            startActivity(Deletepage)

            Toast.makeText(applicationContext, "Loading Delete Page...", Toast.LENGTH_SHORT).show()
        } //end listener

        get2btn.setOnClickListener {
            val Deletepage = Intent(applicationContext, Get2::class.java)
            startActivity(Deletepage)

            Toast.makeText(applicationContext, "Loading Get Page...", Toast.LENGTH_SHORT).show()
        } //end listener

        upload2.setOnClickListener {
            val Deletepage = Intent(applicationContext, Upload2::class.java)
            startActivity(Deletepage)

            Toast.makeText(applicationContext, "Loading Upload Page...", Toast.LENGTH_SHORT).show()
        } //end listener


    }
}