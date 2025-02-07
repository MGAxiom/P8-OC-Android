package com.example.vitesseapp.ui.fragments

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.DetailScreenFragmentBinding
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import com.example.vitesseapp.utils.formatDate
import com.example.vitesseapp.utils.formatDateWithYears
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class AddCandidateFragment : Fragment() {
    private var _binding: DetailScreenFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CandidateViewModel by viewModel()
    private var birthday: Long = 0
    private lateinit var pickImage: ActivityResultLauncher<String>
    private var pickedImageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailScreenFragmentBinding.inflate(inflater, container, false)

        pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                pickedImageUri = it
                Glide.with(requireContext())
                    .load(it)
                    .into(binding.imageView)
            }
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.imageView.setOnClickListener {
            launchPhotoPicker()
        }

        binding.datePickerCard.setOnClickListener {
            showDatePicker()
        }

        binding.fabAddCandidate.setOnClickListener {
            if (validateFields()) {
                viewModel.insertCandidate(
                    Candidate(
                        name = binding.editTextFirstName.text.toString()
                                + " "
                                + binding.editTextLastName.text.toString(),
                        phone = binding.editTextPhone.text.toString().toInt(),
                        email = binding.editTextEmail.text.toString(),
                        birthday = birthday,
                        expectedSalary = binding.editTextMoney.text.toString().toInt(),
                        notes = binding.editTextNote.text.toString(),
                        favorite = false,
                        imageResUri = pickedImageUri.toString()
                    )
                ).invokeOnCompletion {
                    navigateToInfoScreen()
                }
            }
        }
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
        } else if (!binding.editTextPhone.text.toString().matches(Regex("^[0-9]+$"))) {
            binding.textInputLayoutPhone.error = "Only numbers are allowed"
            isValid = false
        } else {
            binding.textInputLayoutPhone.error = null
        }

        if (!isValidEmail(binding.editTextEmail.text.toString())) {
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
            ContextThemeWrapper(
                requireContext(),
                R.style.CustomDatePickerDialogTheme
            ),
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

    private fun navigateToInfoScreen() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createdId.collect { id ->
                id.let {
                    findNavController().navigate(
                        AddCandidateFragmentDirections.actionDetailScreenFragmentToInfoScreenFragment(id)
                    )
                }
            }
        }
    }


    private fun launchPhotoPicker() {
        pickImage.launch("image/*")
    }
}
