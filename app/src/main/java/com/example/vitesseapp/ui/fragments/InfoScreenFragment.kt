package com.example.vitesseapp.ui.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.InfoScreenFragmentBinding
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import com.example.vitesseapp.ui.viewmodels.ExchangeApiViewModel
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
    private val exchangeViewModel: ExchangeApiViewModel by viewModel()

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
            findNavController().navigate(R.id.homeFragment)
        }
        viewModel.loadCandidateById(args.candidateId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentCandidate.collect { candidate ->
                withContext(Dispatchers.Main) {
                    candidate?.let {
                        displayCandidateInfo(it)
                        setupContactButton(it)
                        setupFavoriteButton()
                        setupDeleteButton(it)
                        setupEditButton(it)
                    }
                }
            }
        }
    }

    private suspend fun displayCandidateInfo(candidate: Candidate) {
        Glide.with(requireContext())
            .load(Uri.parse(candidate.imageResUri))
            .placeholder(R.drawable.avatar_gris_placeholder)
            .into(binding.candidateImageInfo)
        binding.toolbar.title = candidate.name
        binding.notesText.text = candidate.notes
        binding.birthdayText.text = formatDateWithYears(candidate.birthday)
        binding.salaryEurText.text = "${candidate.expectedSalary} €"
        viewLifecycleOwner.lifecycleScope.launch {
            exchangeViewModel.convertCurrency((candidate.expectedSalary).toDouble())
            exchangeViewModel.exchangeRate.collect() { convertedSalary ->
                binding.salaryGbpText.text = "or £${convertedSalary} "
            }
        }
        updateFavoriteIcon(candidate.favorite)
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

    private fun setupFavoriteButton() {
        val favoriteButton = binding.favoriteButton
        favoriteButton.isLongClickable = true
        favoriteButton.setOnClickListener {
            viewModel.toggleFavorite()
        }
        TooltipCompat.setTooltipText(favoriteButton, "Favorites")
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.favoriteButton.setImageResource(
            if (isFavorite) R.drawable.favorite_star_24 else R.drawable.star_outline
        )
    }

    private fun setupDeleteButton(currentCandidate: Candidate) {
        val deleteButton = binding.deleteButton
        deleteButton.isLongClickable = true
        deleteButton.setOnClickListener {
            showDeleteButtonDialog(currentCandidate)
        }
        TooltipCompat.setTooltipText(deleteButton, "Delete")
    }

    private fun setupEditButton(currentCandidate: Candidate) {
        val editButton = binding.editButton
        editButton.isLongClickable = true
        TooltipCompat.setTooltipText(editButton, "Edit")
        editButton.setOnClickListener {
            val action = currentCandidate.id?.let { candidate ->
                InfoScreenFragmentDirections.actionInfoScreenFragmentToDetailScreenFragment(
                    candidate, true)
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
    }

    private fun showDeleteButtonDialog(currentCandidate: Candidate) {
        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
            .setTitle("Deletion")
            .setMessage("Are you sure you want to delete this candidate?")
            .setPositiveButton("Confirm") { _, _ ->
                viewModel.deleteCandidate(currentCandidate)
                Toast.makeText(requireContext(), "Candidate deleted", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.homeFragment)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}