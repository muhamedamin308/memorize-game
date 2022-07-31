package com.rkpandey.mymemory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rkpandey.mymemory.models.BoardSize
import com.rkpandey.mymemory.models.MemoryCard
import com.rkpandey.mymemory.models.MemoryGame
import com.rkpandey.mymemory.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MainActivity"
    }
    private lateinit var rvBoard : RecyclerView
    private lateinit var clRoot : ConstraintLayout
    private lateinit var memoryGame : MemoryGame
    private lateinit var adapter : MemoryBoardAdapter
    private lateinit var tvNumMoves : TextView
    private lateinit var tvNumPairs : TextView
    private var boardSize : BoardSize = BoardSize.EASY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clRoot = findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)
        memoryGame = MemoryGame(boardSize)

        adapter = MemoryBoardAdapter(this,boardSize, memoryGame.cards, object :MemoryBoardAdapter.CardClickListener{
            override fun onCardClick(position: Int) {
                updateGameWithFlip(position)
            }

        })
        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this,boardSize.getWidth())


    }

    private fun updateGameWithFlip(position: Int) {
        if (memoryGame.haveWonGame()){
            Snackbar.make(clRoot, "You already won!!", Snackbar.LENGTH_LONG).show()
            return
        }
        if (memoryGame.isCardFaceUp(position)){

            Snackbar.make(clRoot, "Invalid Move", Snackbar.LENGTH_LONG).show()
            return
        }
        if (memoryGame.flipCard(position)){
            Log.i(TAG, "Found a match , number of pairs ${memoryGame.numPairsFound}")
        }
        adapter.notifyDataSetChanged()
    }
}