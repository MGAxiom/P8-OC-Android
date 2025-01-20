package com.example.vitesseapp.ui.fragments

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.DetailScreenFragmentBinding
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import com.example.vitesseapp.utils.formatDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class AddCandidateFragment : Fragment() {
    private var _binding: DetailScreenFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: AddCandidateFragmentArgs by navArgs()
    private val viewModel: CandidateViewModel by viewModel()
    private var birthday: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.datePickerCard.setOnClickListener {
            showDatePicker()
        }

        val isEditMode = args.isEditMode
        updateToolbarTitle(isEditMode)

        binding.fabAddCandidate.setOnClickListener {
            if (validateFields()) {
                viewModel.insertCandidate(
                    Candidate(
                        name = binding.editTextFirstName.text.toString() + " " + binding.editTextLastName.text.toString(),
                        phone = binding.editTextPhone.text.toString().toInt(),
                        email = binding.editTextEmail.text.toString(),
                        birthday = birthday,
                        expectedSalary = binding.editTextMoney.text.toString().toInt(),
                        notes = binding.editTextNote.text.toString(),
                        favorite = false,
                        imageResId = binding.imageView.id
                    )

                )
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val birthdayMillis = selectedDate.timeInMillis

                binding.editTextDate.setText(formatDate(birthdayMillis))
                birthday = birthdayMillis
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return emailPattern.matches(email)
    }
}
