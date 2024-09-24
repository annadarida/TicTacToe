package com.example.tictactoe.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.R
import com.example.tictactoe.databinding.GameFragmentLayoutBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class GameFragment : Fragment() {

    private var _binding: GameFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by activityViewModel<GameViewModel>()

    private var gridMap: HashMap<String, View> = hashMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GameFragmentLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        viewModel.setUpPlayers()

        gridMap = hashMapOf(
            "1 1" to binding.iv11,
            "1 2" to binding.iv12,
            "1 3" to binding.iv13,
            "2 1" to binding.iv21,
            "2 2" to binding.iv22,
            "2 3" to binding.iv23,
            "3 1" to binding.iv31,
            "3 2" to binding.iv32,
            "3 3" to binding.iv33
        )

        viewModel.startGame()
        viewModel.updateCell.observe(viewLifecycleOwner) {
            if (it.shouldClearAllGame) {
                gridMap.values.forEach {
                    val image = it as ImageView
                    image.setImageResource(0)
                }
            } else {
                val view = gridMap["""${it.row} ${it.column}"""] as ImageView
                view.setImageResource(if (it.isX) R.drawable.pizza_x else R.drawable.donut_o)
            }
        }
        //Show score of last round as a dialog
        viewModel.scoreData.observe(viewLifecycleOwner) {
            it?.let {
                score ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("This is the score of the last game")
                builder.setMessage("${score.playerX} : ${score.scoreX} - ${score.playerO} : ${score.scoreO}" )
                builder.setPositiveButton("Okay") { dialog, id ->
                    viewModel.clearGame()
                    viewModel.startGame()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.grid11.setOnClickListener {
            viewModel.chooseSpace(1, 1)
        }
        binding.grid12.setOnClickListener {
            viewModel.chooseSpace(1, 2)
        }
        binding.grid13.setOnClickListener {
            viewModel.chooseSpace(1, 3)
        }
        binding.grid21.setOnClickListener {
            viewModel.chooseSpace(2, 1)
        }
        binding.grid22.setOnClickListener {
            viewModel.chooseSpace(2, 2)
        }
        binding.grid23.setOnClickListener {
            viewModel.chooseSpace(2, 3)
        }
        binding.grid31.setOnClickListener {
            viewModel.chooseSpace(3, 1)
        }
        binding.grid32.setOnClickListener {
            viewModel.chooseSpace(3, 2)
        }
        binding.grid33.setOnClickListener {
            viewModel.chooseSpace(3, 3)
        }

        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
        }
    }

}