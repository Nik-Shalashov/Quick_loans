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
import ru.android.shift_education.databinding.FragmentRegistrationBinding
import ru.android.shift_education.domain.entities.AuthEntity
import ru.android.shift_education.domain.entities.UserEntity
import ru.android.shift_education.presentation.appState.AppState
import ru.android.shift_education.presentation.viewmodel.auth.AuthViewModel

@AndroidEntryPoint
class RegistrationFragment: Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authResponseBody = AuthEntity(
            name = binding.etEnterLogin.text.toString(),
            password = binding.etEnterPassword.text.toString()
        )
        viewModel.validateFields(authResponseBody)
        binding.buttonConfirmRegistration.setOnClickListener {
            viewModel.registerLiveData.observe(viewLifecycleOwner) {renderData(it)}
            viewModel.isValidateTextLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.registerInApp(authResponseBody)
                } else {
                    binding.fragmentRegistration.showSnackBar("Поля пустые")
                }
            }
        }
    }

    private fun renderData(appState: AppState<UserEntity?>) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.fragmentRegistration.showSnackBar("Теперь вы зарегистрированы!")
                goToFragment(AuthFragment.newInstance())
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.fragmentRegistration.showSnackBar(appState.error!!)
            }
        }
    }

    private fun goToFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction().replace(R.id.container, fragment)
                .commitAllowingStateLoss()
        }
    }

    private fun View.showSnackBar(
        text: String,
        length: Int = Snackbar.LENGTH_LONG
    ) {
        Snackbar.make(this, text, length).show()
    }

    companion object {
        fun newInstance(bundle: Bundle): RegistrationFragment {
            val fragment = RegistrationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}