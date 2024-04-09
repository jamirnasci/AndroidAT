package com.jamir.aligntool.algorithms

import android.util.Log
import com.jamir.aligntool.entities.Result

class AlignmentAlgorithms {
    companion object{
        fun needlemanWunsch(
            seq1: String,
            seq2: String,
            matchScore: Int,
            mismatchScore: Int,
            gapPenalty: Int
        ): Result {
            val scoreMatrix = Array(seq1.length + 1) { IntArray(seq2.length + 1) }
            val tracebackMatrix = Array(seq1.length + 1) { Array(seq2.length + 1) { "" } }

            // Initialize the scoring matrix and traceback matrix
            for (i in 0..seq1.length) {
                scoreMatrix[i][0] = i * gapPenalty
                tracebackMatrix[i][0] = "up"
            }
            for (j in 0..seq2.length) {
                scoreMatrix[0][j] = j * gapPenalty
                tracebackMatrix[0][j] = "left"
            }

            // Fill in the scoring and traceback matrices
            for (i in 1..seq1.length) {
                for (j in 1..seq2.length) {
                    val match = scoreMatrix[i - 1][j - 1] + if (seq1[i - 1] == seq2[j - 1]) matchScore else mismatchScore
                    val gapSeq1 = scoreMatrix[i - 1][j] + gapPenalty
                    val gapSeq2 = scoreMatrix[i][j - 1] + gapPenalty
                    scoreMatrix[i][j] = maxOf(match, maxOf(gapSeq1, gapSeq2))

                    when (scoreMatrix[i][j]) {
                        match -> tracebackMatrix[i][j] = "diagonal"
                        gapSeq1 -> tracebackMatrix[i][j] = "up"
                        else -> tracebackMatrix[i][j] = "left"
                    }
                }
            }

            // Traceback to construct the aligned sequences
            var align1 = ""
            var align2 = ""
            var i = seq1.length
            var j = seq2.length

            while (i > 0 || j > 0) {
                when (tracebackMatrix[i][j]) {
                    "diagonal" -> {
                        align1 = seq1[i - 1] + align1
                        align2 = seq2[j - 1] + align2
                        i--
                        j--
                    }
                    "up" -> {
                        align1 = seq1[i - 1] + align1
                        align2 = "-$align2"
                        i--
                    }
                    "left" -> {
                        align1 = "-$align1"
                        align2 = seq2[j - 1] + align2
                        j--
                    }
                }
            }

            var totalGaps = 0
            var matches = 0
            var mismatches = 0
            for(i in 0 .. (align1.length - 1)){
                if(align1[i] == align2[i] && align1[i] != '-'){
                    matches++
                }else if (align1[i] != align2[i] && align1[i] != '-' && align2[i] != '-'){
                    mismatches++
                }else{
                    totalGaps++
                }
            }
            val similarity:Float = matches * 100.0f / align1.length
            return Result(scoreMatrix[seq1.length][seq2.length], similarity, align1.length,matches, totalGaps, mismatches)
        }


        fun needlemanWunschAminoacid(
            seq1: String,
            seq2: String,
            gapPenalty: Int
        ): Result {
            val scoreMatrix = Array(seq1.length + 1) { IntArray(seq2.length + 1) }
            val tracebackMatrix = Array(seq1.length + 1) { Array(seq2.length + 1) { "" } }

            // Initialize the scoring matrix and traceback matrix
            for (i in 0..seq1.length) {
                scoreMatrix[i][0] = i * gapPenalty
                tracebackMatrix[i][0] = "up"
            }
            for (j in 0..seq2.length) {
                scoreMatrix[0][j] = j * gapPenalty
                tracebackMatrix[0][j] = "left"
            }

            // Fill in the scoring and traceback matrices
            for (i in 1..seq1.length) {
                for (j in 1..seq2.length) {
                    val aminoAcidIndex1 = BLOSUM62.aminoAcids.indexOf(seq1[i - 1])
                    val aminoAcidIndex2 = BLOSUM62.aminoAcids.indexOf(seq2[j - 1])
                    val match = scoreMatrix[i - 1][j - 1] + BLOSUM62.blosum62[aminoAcidIndex1][aminoAcidIndex2]
                    val gapSeq1 = scoreMatrix[i - 1][j] + gapPenalty
                    val gapSeq2 = scoreMatrix[i][j - 1] + gapPenalty
                    scoreMatrix[i][j] = maxOf(match, maxOf(gapSeq1, gapSeq2))

                    when (scoreMatrix[i][j]) {
                        match -> tracebackMatrix[i][j] = "diagonal"
                        gapSeq1 -> tracebackMatrix[i][j] = "up"
                        else -> tracebackMatrix[i][j] = "left"
                    }
                }
            }

            // Traceback to construct the aligned sequences
            var align1 = ""
            var align2 = ""
            var i = seq1.length
            var j = seq2.length

            while (i > 0 || j > 0) {
                when (tracebackMatrix[i][j]) {
                    "diagonal" -> {
                        align1 = seq1[i - 1] + align1
                        align2 = seq2[j - 1] + align2
                        i--
                        j--
                    }
                    "up" -> {
                        align1 = seq1[i - 1] + align1
                        align2 = "-$align2"
                        i--
                    }
                    "left" -> {
                        align1 = "-$align1"
                        align2 = seq2[j - 1] + align2
                        j--
                    }
                }
            }

            var totalGaps = 0
            var matches = 0
            var mismatches = 0
            for (k in 0 until align1.length) {
                if (align1[k] == align2[k] && align1[k] != '-') {
                    matches++
                } else if (align1[k] != align2[k] && align1[k] != '-' && align2[k] != '-') {
                    mismatches++
                } else {
                    totalGaps++
                }
            }
            val similarity: Float = matches * 100.0f / align1.length
            return Result(scoreMatrix[seq1.length][seq2.length], similarity, align1.length, matches, totalGaps, mismatches)
        }
    }
}