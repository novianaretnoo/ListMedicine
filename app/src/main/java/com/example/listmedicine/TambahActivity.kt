package com.example.listmedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahActivity : AppCompatActivity() {

    private lateinit var inputNama : EditText
    private lateinit var inputStok : EditText

    //deklarasi firebase dabes reference
    //Untuk membaca atau menulis data dari database memerlukan instance DatabaseReference
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)

        //reference firebase database di class TambahActivity
        ref = FirebaseDatabase.getInstance().getReference("datas")

        inputNama = findViewById(R.id.inputNama)
        inputStok = findViewById(R.id.inputStok)

        //perintah untuk save data dari button save
        val btnSave : Button = findViewById(R.id.btnsave)
        btnSave.setOnClickListener{
            savedata()
            val intent = Intent (this, ShowActivity::class.java)
            startActivity(intent)
        }
    }

    private fun savedata() {
        val nama = inputNama.text.toString()
        val stok = inputStok.text.toString()

        val dataId = ref.push().key.toString()
        val data = Datas(dataId,nama,stok)

        //setValue() untuk menyimpan maupun me-replace data yang ada pada spesifik reference.
        ref.child(dataId).setValue(data).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            inputNama.setText("")
            inputStok.setText("")
        }
    }
}