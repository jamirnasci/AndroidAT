package com.jamir.aligntool

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jamir.aligntool.algorithms.AlignmentAlgorithms
import com.jamir.aligntool.databinding.ActivitySequenceBinding
import com.jamir.aligntool.entities.Result

class SequenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySequenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySequenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type:String? = intent.getStringExtra("type")
        if(type.equals("AMINOACID")) {
            binding.matchSpinner.visibility = View.GONE
            binding.matchLabel.visibility = View.GONE
            binding.mismatchSpinner.visibility = View.GONE
            binding.mismatchLabel.visibility = View.GONE
        }

        binding.alignBtn.setOnClickListener {
            val resultIntent: Intent = Intent(applicationContext, ResultActivity::class.java)
            val querySeq = binding.querySeqArea.text.toString()
            val subjectSeq = binding.subjectSeqArea.text.toString()
            val match = binding.matchSpinner.selectedItem.toString().toInt()
            val mismatch = binding.mismatchSpinner.selectedItem.toString().toInt()
            val gap = binding.gapSpinner.selectedItem.toString().toInt()
            if(querySeq.isEmpty() || subjectSeq.isEmpty()){
                Toast.makeText(applicationContext, "Insira as 2 sequencias", Toast.LENGTH_LONG).show()
            }else{
                if(type.equals("NUCLEOTIDE")){
                    val result: Result = AlignmentAlgorithms.needlemanWunsch(querySeq, subjectSeq, match, mismatch, gap)
                    resultIntent.putExtra("result", result)
                }else if(type.equals("AMINOACID")){
                    binding.matchSpinner.visibility = View.GONE
                    binding.matchLabel.visibility = View.GONE
                    binding.mismatchSpinner.visibility = View.GONE
                    binding.mismatchLabel.visibility = View.GONE
                    val result: Result = AlignmentAlgorithms.needlemanWunschAminoacid(querySeq, subjectSeq, gap)
                    resultIntent.putExtra("result", result)
                }
                startActivity(resultIntent)
            }
        }
    }
}