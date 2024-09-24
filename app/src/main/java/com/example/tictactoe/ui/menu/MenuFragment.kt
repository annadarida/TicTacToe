package com.example.tictactoe.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.databinding.MenuLayoutBinding
import com.example.tictactoe.ui.game.GameViewModel
import kotlin.system.exitProcess

class MenuFragment : Fragment() {

    private var _binding: MenuLayoutBinding? = null
    val binding: MenuLayoutBinding
        get() = _binding!!

    val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MenuLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnScore.setOnClickListener {
                viewModel.menuNavigate(NavigationType.HISTORY)
            }
            btnNewGame.setOnClickListener {
                viewModel.menuNavigate(NavigationType.NEW)
            }
            btnExit.setOnClickListener {
                exitProcess(0)
            }
            btnAbout.setOnClickListener {
                Log.d("**GAME**", "About button was pressed")
                viewModel.menuNavigate(NavigationType.ABOUT)
            }
        }
        setUpObservable()
    }

    private fun setUpObservable() {
        viewModel.navigateAction.observe(viewLifecycleOwner) {
            it?.let { direction ->
                findNavController().navigate(direction)
                viewModel.clearNavigation()
            }
        }
    }
}