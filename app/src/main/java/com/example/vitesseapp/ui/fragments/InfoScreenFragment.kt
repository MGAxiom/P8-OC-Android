package com.example.vitesseapp.ui.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.InfoScreenFragmentBinding
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import com.example.vitesseapp.utils.formatDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

@RequiresApi(Build.VERSION_CODES.O)
class InfoScreenFragment : Fragment() {
    private var _binding: InfoScreenFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: InfoScreenFragmentArgs by navArgs()
    private val viewModel: CandidateViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InfoScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getCandidateById(args.candidateId).collect { candidate ->
                withContext(Dispatchers.Main) {
                    candidate?.let {
                        displayCandidateInfo(it)
                    }
                }
            }
        }
    }


    private fun displayCandidateInfo(candidate: Candidate) {
        binding.candidateImageInfo.setImageURI(Uri.parse(candidate.imageResUri))
        binding.toolbar.title = candidate.name
        binding.notesText.text = candidate.notes
        binding.birthdayText.text = formatDate(candidate.birthday)
        binding.salaryEurText.text = "${candidate.expectedSalary} €"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}