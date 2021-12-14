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
import ru.android.shift_education.databinding.FragmentAuthBinding
import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.presentation.appState.AppState
import ru.android.shift_education.presentation.viewmodel.auth.AuthViewModel

@AndroidEntryPoint
class AuthFragment: Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authResponseBody = AuthEntity(
            name = binding.etLogin.text.toString(),
            password = binding.etPassword.text.toString()
        )
        viewModel.isAuthorized()
        viewModel.validateFields(authResponseBody)
        binding.buttonEnter.setOnClickListener {
            viewModel.loginLiveData.observe(viewLifecycleOwner) {renderData(it)}
            viewModel.isValidateTextLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.loginIntoApp(authResponseBody)
                } else {
                    binding.fragmentAuth.showSnackBar("Поля пустые")
                }
            }
        }
        binding.buttonRegistration.setOnClickListener {
            activity?.supportFragmentManager?.apply {
                beginTransaction().replace(R.id.container, RegistrationFragment.newInstance(Bundle()))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
        viewModel.authLiveData.observe(viewLifecycleOwner) {
            if (it) goToFragment(MainFragment.newInstance(Bundle()))
        }
    }

    private fun renderData(appState: AppState<String?>) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                appState.content?.let { viewModel.saveAuthToken(it) }
                goToFragment(viewModel.chooseScreen())
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.fragmentAuth.showSnackBar(appState.error!!)
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
        fun newInstance() =
            AuthFragment()
    }

}