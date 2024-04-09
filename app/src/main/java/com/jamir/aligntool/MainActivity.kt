package com.jamir.aligntool

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jamir.aligntool.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private val NUCLEOTIDE = "NUCLEOTIDE"
    private val AMINOACID = "AMINOACID"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent: Intent = Intent(applicationContext, SequenceActivity::class.java)

        binding.nuclAlnBtn.setOnClickListener {
            intent.putExtra("type", NUCLEOTIDE)
            startActivity(intent)
        }
        binding.aminoAlnBtn.setOnClickListener {
            intent.putExtra("type", AMINOACID)
            startActivity(intent)
        }
    }
}