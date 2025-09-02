package com.example.a1helloandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.a1helloandroid.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create a list of the buttons
        val buttons = listOf(
            binding.buttonOne to "Button One",
            binding.buttonTwo to "Button Two",
            binding.buttonThree to "Button Three",
            binding.buttonFour to "Button Four",
            binding.buttonFive to "Button Five"
        )

        buttons.forEach { (button, text) ->
            button.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("buttonText", text)
                }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}