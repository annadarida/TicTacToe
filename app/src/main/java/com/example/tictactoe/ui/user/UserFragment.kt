package com.example.tictactoe.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.databinding.UserLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment() {
    private val viewModel: UserViewModel by viewModel<UserViewModel>()
    private var _binding: UserLayoutBinding? = null
    private val binding: UserLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var name1: String
        var name2: String
        //When next is clicked check if edit text has valid names
        binding.startGameButton.setOnClickListener {
            if (binding.name1.text.toString().isNotEmpty() &&
                binding.name2.text.toString().isNotEmpty()
            ) {
                name1 = binding.name1.text.toString()
                name2 = binding.name2.text.toString()
                viewModel.setUpPlayers(name1, name2)
            }
        }
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.navigateState.observe(viewLifecycleOwner) { it ->
            findNavController().navigate(it)
        }
    }
}