package ru.android.shift_education.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.android.shift_education.R
import ru.android.shift_education.databinding.FragmentMainBinding
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.presentation.adapter.MainAdapter
import ru.android.shift_education.presentation.appState.AppState
import ru.android.shift_education.presentation.viewmodel.main.MainViewModel

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private val adapter = MainAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(loanEntity: LoanEntity) {
            activity?.supportFragmentManager?.apply {
                beginTransaction().add(R.id.container, LoanDescriptionFragment.newInstance(Bundle().apply {
                    putParcelable(LoanDescriptionFragment.BUNDLE_EXTRA, loanEntity)
                }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewCurrencies.adapter = adapter
        binding.btnAddLoan.setOnClickListener {
            activity?.supportFragmentManager?.apply {
                beginTransaction().replace(R.id.container, NewLoanFragment())
                    .commitAllowingStateLoss()
            }
        }
        viewModel.loansListLiveData.observe(viewLifecycleOwner, {renderData(it)})
        viewModel.getLoansList()
    }

    private fun renderData(appState: AppState<List<LoanEntity>>) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                val listLoans = appState.content
                adapter.setLoan(listLoans)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.fragmentLoansHistory.showSnackBar(appState.error!!)
            }
        }
    }

    private fun View.showSnackBar(
        text: String,
        length: Int = Snackbar.LENGTH_LONG
    ) {
        Snackbar.make(this, text, length).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(loanEntity: LoanEntity)
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    companion object {
        fun newInstance(bundle: Bundle): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}