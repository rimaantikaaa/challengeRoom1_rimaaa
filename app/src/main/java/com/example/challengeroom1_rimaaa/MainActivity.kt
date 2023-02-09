package com.example.challengeroom1_rimaaa

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom1_rimaaa.room.Constant
import com.example.challengeroom1_rimaaa.room.dbsmksa
import com.example.challengeroom1_rimaaa.room.tbsiswa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val db by lazy { dbsmksa(this) }
    private lateinit var siswaAdapter: SiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()

    }
    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val siswa = db.tbsisDao().tampilsemua()
            Log.d("MainActivity", "dbResponse:$siswa")
            withContext(Dispatchers.Main){
                siswaAdapter.setData(siswa)
            }
        }
    }
    fun intentEdit(tbsis_nis: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_nis", tbsis_nis)
                .putExtra("intent_type", intentType)
        )
    }
    private fun halEdit() {
        super.onStart()
        btnInput.setOnClickListener {
            startActivity(Intent(this,EditActivity::class.java))
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }
    fun setupRecyclerView() {
        siswaAdapter = SiswaAdapter(arrayListOf(),object : SiswaAdapter.OnAdapterListener{
            override fun onClick(tbsis: tbsiswa) {
                intentEdit(tbsis.nis,Constant.TYPE_READ)
            }

            override fun onUpdate(tbsis: tbsiswa) {
                intentEdit(tbsis.nis,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbsis: tbsiswa) {
                deleteDialog(tbsis)
            }
        })
        listData.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }
    private fun deleteDialog(tbsis: tbsiswa) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${tbsis.nama}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbsisDao().deletetbsiswa(tbsis)
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}

