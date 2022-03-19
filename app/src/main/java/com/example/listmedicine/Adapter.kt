package com.example.listmedicine

import android.app.AlertDialog
import android.app.ProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Datas> )
    : ArrayAdapter<Datas>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //Layout Inflater: menghubungkan MainActivity dgn Sub Activu\ity
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textNama = view.findViewById<TextView>(R.id.textNama)
        val textStok = view.findViewById<TextView>(R.id. textStok)

        val textUpdate = view.findViewById<TextView>(R.id.textUpdate)
        val textDelete = view.findViewById<TextView>(R.id.textDelete)

        //memposisikan data sesuai dgn position terakhir read di database
        val data = list[position]

        textNama.text = data.nama
        textStok.text = data.stok

        textUpdate.setOnClickListener {
            showUpdateDialog(data)
        }
        textDelete.setOnClickListener {
            Deleteinfo(data)
        }
        return view

    }

    private fun showUpdateDialog(data: Datas) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textNama = view.findViewById<EditText>(R.id.inputNama)
        val textStok = view.findViewById<EditText>(R.id.inputStok)


        textNama.setText(data.nama)
        textStok.setText(data.stok)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("datas")

            val nama = textNama.text.toString().trim()

            val stok = textStok.text.toString().trim()

            if (nama.isEmpty()) {
                textNama.error = "please enter name"
                textNama.requestFocus()
                return@setPositiveButton
            }

            if (stok.isEmpty()) {
                textStok.error = "please enter status"
                textStok.requestFocus()
                return@setPositiveButton
            }

            val data = Datas(data.id, nama, stok)

            dbUsers.child(data.id).setValue(data).addOnCompleteListener {
                Toast.makeText(mCtx, "Updated", Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()
    }

    private fun Deleteinfo(data: Datas) {
        val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("datas")
        mydatabase.child(data.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ShowActivity::class.java)
        context.startActivity(intent)
    }
}