package com.example.vitesseapp.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.InfoScreenFragmentBinding
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import com.example.vitesseapp.utils.callCandidate
import com.example.vitesseapp.utils.emailCandidate
import com.example.vitesseapp.utils.formatDateWithYears
import com.example.vitesseapp.utils.messageCandidate
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
            findNavController().navigate(R.id.action_global_home)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCandidateById(args.candidateId).collect { candidate ->
                withContext(Dispatchers.Main) {
                    candidate?.let {
                        displayCandidateInfo(it)
                        setupContactButton(it)
                        toggleFavoriteButton(it)
                    }
                }
            }
        }
    }

    private fun displayCandidateInfo(candidate: Candidate) {
        Glide.with(requireContext())
            .load(Uri.parse(candidate.imageResUri))
            .placeholder(R.drawable.avatar_gris_placeholder)
            .into(binding.candidateImageInfo)
        binding.toolbar.title = candidate.name
        binding.notesText.text = candidate.notes
        binding.birthdayText.text = formatDateWithYears(candidate.birthday)
        binding.salaryEurText.text = "${candidate.expectedSalary} €"
        setupFavoriteIcon(candidate.favorite)
    }

    private fun setupContactButton(candidate: Candidate) {
        binding.circleIconPhoneButton.setOnClickListener {
            callCandidate(candidate.phone, requireContext())
        }
        binding.circleIconSmsButton.setOnClickListener {
            messageCandidate(candidate.phone, requireContext())
        }
        binding.circleIconMailButton.setOnClickListener {
            emailCandidate(candidate.email, requireContext())
        }
    }

    private fun toggleFavoriteButton(candidate: Candidate) {
        binding.favoriteButton.setOnClickListener {
            viewModel.toggleFavorite(candidate)
            binding.favoriteButton.setImageResource( if (candidate.favorite)
                R.drawable.favorite_star_24 else R.drawable.star_outline
            )
        }
    }

    private fun setupFavoriteIcon(isFavorite: Boolean) {
        binding.favoriteButton.setImageResource( if (isFavorite)
            R.drawable.favorite_star_24 else R.drawable.star_outline
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}