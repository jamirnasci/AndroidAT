package com.jamir.aligntool

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.jamir.aligntool.databinding.ActivityResultBinding
import com.jamir.aligntool.entities.Result
import java.io.BufferedWriter
import java.io.File
import java.io.PrintWriter

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        val result:Result = intent.getSerializableExtra("result") as Result
        val matchPercent: Float = (result.matches * 100.0f) / result.size
        val mismatchPercent: Float = (result.mismatches * 100.0f) / result.size
        val gapPercent: Float = (result.gaps * 100.0f) / result.size
        val pieChart:PieChart = binding.pieChart

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(matchPercent, "%Match"))
        entries.add(PieEntry(mismatchPercent, "%Mismatch"))
        entries.add(PieEntry(gapPercent, "%Gap"))
        binding.similarityValue.text = result.similarity.toString() + "%"
        binding.scoreValue.text = result.score.toString()
        binding.sizeValue.text = result.size.toString()

        val dataset = PieDataSet(entries, "")
        dataset.colors = listOf(
            Color.rgb(63, 81, 181),
            Color.rgb(33, 150, 243),
            Color.rgb(0, 188, 212)
        )

        val data = PieData(dataset)
        data.setValueTextSize(20f)
        data.setValueTextColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(20f)
        pieChart.description.text = ""
        pieChart.data = data
        pieChart.legend.textSize = 20f
        pieChart.invalidate()
    }

}