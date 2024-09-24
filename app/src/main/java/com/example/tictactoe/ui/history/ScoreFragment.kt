package com.example.tictactoe.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.entity.Player
import com.example.tictactoe.entity.Scores
import com.example.tictactoe.databinding.ScoreLayoutBinding

class ScoreFragment: Fragment() {

    var _binding: ScoreLayoutBinding? = null
    val binding: ScoreLayoutBinding
        get() = _binding!!
    val viewModel: ScoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScoreLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setScoreList(
            arguments?.getSerializable("listOfScore") as Scores
        )
        viewModel.setUpPlayers(
            arguments?.getSerializable("player1") as Player,
            arguments?.getSerializable("player2") as Player
        )

        val recyclerview = binding.rvDesign

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // ArrayList of class ItemsViewModel
        viewModel.scoreList.observe(viewLifecycleOwner) {
            val adapter = RecyclerAdapter(it)
            recyclerview.adapter = adapter
        }

        var player1: Player? = null
        var player2: Player? = null

        viewModel.player1.observe(viewLifecycleOwner) {
            player1 = it
            Log.d("TICTAC$", "player 1 is " + player1?.name)
        }

        viewModel.player2.observe(viewLifecycleOwner) {
            player2 = it
            Log.d("TICTAC$", "player 2 is " + player2?.name)
        }

        if (player1 != null && player2 != null) {
            println("""${player1!!.name} ${player1!!.score} - ${player2!!.score} ${player2!!.name} """)
            binding.mainScore.text = """${player1!!.name} ${player1!!.score} - ${player2!!.score} ${player2!!.name} """
        }
    }
}