package com.example.employeeapp

import android.content.Context
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject
class ApiHelper(var context: Context) {
    //POST
    fun post(api: String, jsonData: JSONObject){
        Toast.makeText(context, "Please Wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient(true, 80, 443)
        val con_body = StringEntity(jsonData.toString())
        //post to API
        client.post(context, api, con_body, "application/json",
            object : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "Response $response ", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse)
                    //Todo handle the error
                    Toast.makeText(context, "Error Occurred"+throwable.toString(), Toast.LENGTH_LONG).show()
                    // progressbar.visibility = View.GONE
                }
            })
    }//END POST

    //GET
    fun get(api: String, callBack: CallBack) {
        val client = AsyncHttpClient(true, 80, 443)
        //GET to API
        client.get(context, api, null, "application/json",
            object : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray
                ) {
                    callBack.onSuccess(response.toString())
                    //Toast.makeText(context, "Response $response ", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(context, "Error Occurred"+throwable.toString(), Toast.LENGTH_LONG).show()
                }

            })

    }//END GET

    //PUT
    fun put(api: String, jsonData: JSONObject){
        Toast.makeText(context, "Please Wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient(true, 80, 443)
        val con_body = StringEntity(jsonData.toString())
        //PUT to API
        client.put(context, api, con_body, "application/json",
            object : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "Response $response ", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse)
                    //Todo handle the error
                    Toast.makeText(context, "Error Occurred"+throwable.toString(), Toast.LENGTH_LONG).show()
                    // progressbar.visibility = View.GONE
                }
            })
    }//END PUT

    //DELETE
    fun delete(api: String, jsonData: JSONObject){
        Toast.makeText(context, "Please Wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient(true, 80, 443)
        val con_body = StringEntity(jsonData.toString())
        //DELETE to API
        client.delete(context, api, con_body, "application/json",
            object : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "Response $response ", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse)
                    //Todo handle the error
                    Toast.makeText(context, "Error Occurred"+throwable.toString(), Toast.LENGTH_LONG).show()
                    // progressbar.visibility = View.GONE
                }
            })
    }//END DELETE

    //Interface to used by the GET function above.
    interface CallBack {
        fun onSuccess(result: String?)
    }

}//END CLASS