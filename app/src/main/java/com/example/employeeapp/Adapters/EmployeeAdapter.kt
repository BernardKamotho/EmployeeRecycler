package com.example.employeeapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.Models.Employee
import com.example.employeeapp.R
import com.google.android.material.textview.MaterialTextView
class EmployeeAdapter(var context: Context):
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {


    //Create a List and connect it with our model
    var itemList : List<Employee> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.ViewHolder {
        //access/inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_employee,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.ViewHolder, position: Int) {
         //Find your 3 text views
        val username = holder.itemView.findViewById<MaterialTextView>(R.id.username)
        val others = holder.itemView.findViewById<MaterialTextView>(R.id.others)
        val salary = holder.itemView.findViewById<MaterialTextView>(R.id.salary)
        val department = holder.itemView.findViewById<MaterialTextView>(R.id.department)
        //Assume one Lab
         val employee = itemList[position]
         username.text = employee.username
        others.text = employee.others
        salary.text = "Kes: "+employee.salary
        department.text = employee.department
        //When one Lab is clicked, Move to Lab tests Activity
         holder.itemView.setOnClickListener {
             //carry the Lab_id of what you clicked.
             //carry it with Bundles, Preferences
             val id = employee.id_number
             //pass this ID along with intent, I prefer shared preferecne
             //Save id to prefs
//             val i = Intent(context, LabTestsActivity::class.java)
//             i.putExtra("key1", id)
//             i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//             context.startActivity(i)
         }
    }

    override fun getItemCount(): Int {
        return itemList.size  //Count how may Items in the List
    }

    //This is for filtering data
    fun filterList(filterList: List<Employee>){
        itemList = filterList
        notifyDataSetChanged()
    }


    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<Employee>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
    //Tell this adapter class that now itemList is loaded with data
    }
    //justpaste.it/cgaym
}