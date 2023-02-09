package com.example.challengeroom1_rimaaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengeroom1_rimaaa.room.Constant
import com.example.challengeroom1_rimaaa.room.dbsmksa
import com.example.challengeroom1_rimaaa.room.tbsiswa
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { dbsmksa(this) }
    private var tbsisNis: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        tombolPerintah()
        tbsisNis = intent.getIntExtra("intent_nis", tbsisNis)
        Toast.makeText(this, tbsisNis.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_simpan.visibility = View.GONE
                btn_update.visibility = View.GONE
                ETnis.visibility = View.GONE
                tampilsemua()
            }
            Constant.TYPE_UPDATE -> {
                btn_simpan.visibility = View.GONE
                ETnis.visibility = View.GONE
                tampilsemua()
            }
        }
    }

    fun tombolPerintah() {
        btn_simpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
             db.tbsisDao().addtbsiswa(
                 tbsiswa(ETnis.text.toString().toInt(),
                        ETnama.text.toString(),
                        ETkelas.text.toString(),
                        ETalamat.text.toString())
             )
             finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsisDao().updatetbsiswa(
                    tbsiswa(tbsisNis,
                        ETnama.text.toString(),
                        ETkelas.text.toString(),
                        ETalamat.text.toString())
                )
                finish()
            }
        }
    }

    fun tampilsemua(){
        tbsisNis = intent.getIntExtra("intent_nis", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val siswa = db.tbsisDao().tampilid(tbsisNis)[0]

            ETnama.setText(siswa.nama)
            ETkelas.setText(siswa.kelas)
            ETalamat.setText(siswa.alamat)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

