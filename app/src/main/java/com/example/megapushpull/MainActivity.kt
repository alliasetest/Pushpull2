package com.example.megapushpull

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.megapushpull.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var arrayList:ArrayList<MyRecord> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = findViewById<EditText>(R.id.name)
        val age = findViewById<EditText>(R.id.age)
        val city = findViewById<EditText>(R.id.city)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val addData = MyRecord(fname = name.text.toString().trim(),fage = age.text.toString().trim(),fcity = city.text.toString().trim())
            db.collection("pushrepo").add(addData)
                .addOnSuccessListener {
                Toast.makeText(this,"success",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"failure",Toast.LENGTH_LONG).show()
                }
        }

        getData()
    }

    private fun getData(){
        arrayList.clear()
        db.collection("pushrepo")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    it.result?.forEachIndexed { index, item ->
                        val list = MyRecord(fname = item.getString("fname").toString(),
                            fage = item.getString("fage").toString(), fcity = item.getString("fcity").toString())
                        arrayList.add(list)
                    }
                    arrayList.forEachIndexed{ index, myRecord ->
                        Log.d("UserData", "fname: ${myRecord.fname}")
                        Toast.makeText(this,"fname: ${myRecord.fname}",Toast.LENGTH_LONG).show()
                        Log.d("UserData", "fage: ${myRecord.fage}")
                        Toast.makeText(this,"fage: ${myRecord.fage}",Toast.LENGTH_LONG).show()
                        Log.d("UserData", "fcity: ${myRecord.fcity}")
                        Toast.makeText(this,"fcity: ${myRecord.fcity}",Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}