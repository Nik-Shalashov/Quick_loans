package ru.android.shift_education.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.android.shift_education.R
import ru.android.shift_education.databinding.FragmentMainBinding
import ru.android.shift_education.databinding.FragmentNewLoanBinding
import ru.android.shift_education.domain.entities.LoanConditionsEntity
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.domain.entities.LoanRequestEntity
import ru.android.shift_education.presentation.appState.AppState
import ru.android.shift_education.presentation.viewmodel.main.MainViewModel
import ru.android.shift_education.presentation.viewmodel.newLoan.NewLoanViewModel

@AndroidEntryPoint
class NewLoanFragment(): Fragment() {

    private var _binding: FragmentNewLoanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewLoanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loanConditionsLiveData.observe(viewLifecycleOwner, {renderDataConditions(it)})
        viewModel.getLoanConditions()
        viewModel.newLoanLiveData.observe(viewLifecycleOwner, {renderDataNewLoan(it)})
        val loanRequest = LoanRequestEntity(
            amount = binding.sbLoanAmount.progress,
            firstName = binding.etEnterFirstName.text.toString(),
            lastName = binding.etEnterLastName.text.toString(),
            percent = binding.tvPercentSum.text.toString().toDouble(),
            period = binding.tvLoanTermSum.text.toString().toInt(),
            phoneNumber = binding.etEnterPhoneNumber.text.toString()
        )
        viewModel.createNewLoan(loanRequest)
    }

    private fun renderDataConditions(appState: AppState<LoanConditionsEntity>) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.sbLoanAmount.max = appState.content.maxAmount
                binding.tvPercentSum.text = appState.content.percent.toString()
                binding.tvLoanTermSum.text = appState.content.period.toString()
                binding.sbLoanAmount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        binding.tvAmountSum.text = seekBar?.progress.toString()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        binding.tvAmountSum.text = seekBar?.progress.toString()
                    }
                })
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.newLoanFragment.showSnackBar(appState.error!!)
            }
        }
    }

    private fun renderDataNewLoan(appState: AppState<LoanEntity>) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                Toast.makeText(activity?.applicationContext, "Заявка успешно создана!", Toast.LENGTH_LONG).show()
                goToFragment(MainFragment.newInstance(Bundle()))
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.newLoanFragment.showSnackBar(appState.error!!)
            }
        }
    }

    private fun View.showSnackBar(
        text: String,
        length: Int = Snackbar.LENGTH_LONG
    ) {
        Snackbar.make(this, text, length).show()
    }

    private fun goToFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction().replace(R.id.container, fragment)
                .commitAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): NewLoanFragment {
            val fragment = NewLoanFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}