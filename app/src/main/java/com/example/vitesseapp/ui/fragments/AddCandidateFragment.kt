package com.example.vitesseapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vitesseapp.databinding.DetailscreenBinding

class AddCandidateFragment : Fragment() {
    private var _binding: DetailscreenBinding? = null
    private val binding get() = _binding!!
    private val args: AddCandidateFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val isEditMode = args.isEditMode
        updateToolbarTitle(isEditMode)

        binding.fabAddCandidate.setOnClickListener {
            if (validateFields()) {
                updateToolbarTitle(true)
            }
        }
    }


    private fun updateToolbarTitle(isEditMode: Boolean) {
        val title = if (isEditMode) "Edit candidate" else "Add your candidate"
        binding.toolbar.title = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (binding.editTextFirstName.text.isNullOrEmpty()) {
            binding.textInputLayoutFirstName.error = "Mandatory field"
            isValid = false
        } else {
            binding.textInputLayoutFirstName.error = null
        }

        if (binding.editTextLastName.text.isNullOrEmpty()) {
            binding.textInputLayoutLastName.error = "Mandatory field"
            isValid = false
        } else {
            binding.textInputLayoutLastName.error = null
        }

        if (binding.editTextPhone.text.isNullOrEmpty()) {
            binding.textInputLayoutPhone.error = "Mandatory field"
            isValid = false
        } else {
            binding.textInputLayoutPhone.error = null
        }

        if (isValidEmail(binding.editTextEmail.text.toString())) {
            binding.textInputLayoutEmail.error = "Invalid Format"
            isValid = false
        } else {
            binding.textInputLayoutEmail.error = null
        }

        return isValid
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return emailPattern.matches(email)
    }
}
