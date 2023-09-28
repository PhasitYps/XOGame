package com.phasit.gamexo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Sms.Intents
import android.util.Log
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.phasit.gamexo.database.AppDatabase
import com.phasit.gamexo.databinding.ActivityMainBinding
import com.phasit.gamexo.models.HistoryPlay
import com.phasit.gamexo.repository.HistoryPlayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        setEvent()
    }

    private fun setEvent(){

        binding.startBTN.setOnClickListener {
            val input = binding.inputSizeEDT.text.trim().toString()

            if(!input.isDigitsOnly()){
                Toast.makeText(this, "กรุณากำหนดขนาดของ XO ด้วยตัวเลขเท่านั้น", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val size = input.ifEmpty { "3" }
            if(size.toInt() !in 3.. 15){
                Toast.makeText(this, "กรุณากำหนดขนาดของ XO ด้วย 3 ถึง 15", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, PlayGameActivity::class.java)
            intent.putExtra("size", size.toInt())
            startActivity(intent)
        }
    }



}