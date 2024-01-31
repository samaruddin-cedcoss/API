package com.example.task2dynamiclayout

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutlist= findViewById<FrameLayout>(R.id.layoutlist)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://d2idqwn63w1bgz.cloudfront.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val apiService= retrofitBuilder.create(ApiService::class.java)

        val call= apiService.getData()



        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                if (response.isSuccessful) {
                    val response = response.body()

                    val jsonObject = JSONObject(response.toString())

                    val keys = jsonObject.names()

                    val list = mutableListOf<String>()

                   for (i in 1..keys.length()-1)
                    {
                        val key = keys[i]
                        Log.d("Mainnnn", key.toString())
                        list.add(key.toString())

                        val innerObj= jsonObject.getJSONObject(key.toString())
                        val innerKeys = innerObj.keys()

                        while(innerKeys.hasNext())
                        {
                            val innerKey = innerKeys.next()
                            list.add(innerKey.toString()+"\n\n")
                            Log.d("Mainnnn",innerKey)
                        }
                    }



                    createAndAddText(layoutlist,list.toString())




//                            jsonObject?.entrySet()?.forEach { entry ->
//                                val unescapedValue = unescapeJson("${entry.key}")
//                                createAndAddText(layoutlist,unescapedValue)
//                                Log.d(ContentValues.TAG, "${entry.key}")
//                            }
                } else {
                    Log.d(ContentValues.TAG, "Request failed with code : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d(ContentValues.TAG,"On Failure" +t.message)
            }

        })
    }


    private fun createAndAddText(layoutlist: FrameLayout, text: String) {
        val textView= TextView(this)
        textView.text=text

        textView.textSize= 14f

        layoutlist.addView(textView)

        val separator = layoutInflater.inflate(R.layout.separator,layoutlist,false)
        layoutlist.addView(separator)


    }


    private fun unescapeJson(s: String): String {
        return s.replace("\\"," ")
    }
}
