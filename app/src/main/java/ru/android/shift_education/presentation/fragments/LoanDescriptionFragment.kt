package ru.android.shift_education.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.android.shift_education.databinding.FragmentLoanDescriptionBinding
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.presentation.appState.AppState
import ru.android.shift_education.presentation.viewmodel.main.MainViewModel

@AndroidEntryPoint
class LoanDescriptionFragment: Fragment() {

    private var _binding: FragmentLoanDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loan = arguments?.getParcelable<LoanEntity>(BUNDLE_EXTRA)
        if (loan != null) {
            viewModel.loanDataLiveData.observe(viewLifecycleOwner, {renderData(it)})
            viewModel.getLoansData(loan.id.toString())
        }
    }

    private fun renderData(appState: AppState<LoanEntity>) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.tvAmountSum.text = appState.content.amount.toString()
                binding.tvFirstNameValue.text = appState.content.firstName
                binding.tvLastNameValue.text = appState.content.lastName
                binding.tvPercentSum.text = appState.content.percent.toString()
                binding.tvLoanTermSum.text = appState.content.period.toString()
                binding.tvBorrowerPhoneValue.text = appState.content.phoneNumber
                binding.tvLoanStatusValue.text = appState.content.state
                binding.tvLoanDateValue.text = appState.content.date
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.fragmentLoanDescription.showSnackBar(appState.error!!)
            }
        }
    }

    private fun View.showSnackBar(
        text: String,
        length: Int = Snackbar.LENGTH_LONG
    ) {
        Snackbar.make(this, text, length).show()
    }

    companion object {
        const val BUNDLE_EXTRA = "id"
        fun newInstance(bundle: Bundle): LoanDescriptionFragment {
            val fragment = LoanDescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}