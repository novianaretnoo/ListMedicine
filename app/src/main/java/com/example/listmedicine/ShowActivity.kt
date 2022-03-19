package com.example.listmedicine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class ShowActivity : AppCompatActivity() {

    //fungsi utama -> menampilkan data pada show activity
    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Datas>
    lateinit var listView: ListView

    //deklarasi antarmuka activity_show
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        ref = FirebaseDatabase.getInstance().getReference("datas")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        //listener untuk memunculkan list data di firebase
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val data = h.getValue(Datas::class.java)
                        list.add(data!!)
                    }
                    val adapter = Adapter(this@ShowActivity,R.layout.datas,list)
                    listView.adapter = adapter
                }
            }
        })
    }
}