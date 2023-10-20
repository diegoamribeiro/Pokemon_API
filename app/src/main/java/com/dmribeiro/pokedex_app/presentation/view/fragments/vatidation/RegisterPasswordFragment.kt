package com.boxdelivery.courier.ui.register.common

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dmribeiro.pokedex_app.R
import com.dmribeiro.pokedex_app.databinding.FragmentRegisterPasswordBinding
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel.Companion.ERROR_LESS_THAN_EIGHT
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel.Companion.ERROR_MISS_NUMBER
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel.Companion.ERROR_MISS_SPECIAL_CHAR
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel.Companion.ERROR_PASSWORD_CONFIRM_DIFFERENT
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel.Companion.ERROR_PASSWORD_EMPTY
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel.Companion.PASSWORD_MEDIUM
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.RegisterPasswordViewModel.Companion.PASSWORD_WEAK
import com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation.TwoWheelsData
import com.dmribeiro.pokedex_app.utils.viewBinding

class RegisterPasswordFragment : Fragment() {


    private val binding: FragmentRegisterPasswordBinding by viewBinding()
    private val viewModel: RegisterPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setInitRegisterData(TwoWheelsData())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initializeErrors()
        binding.svStepsBar.createStepsBar(5, 5)
        setListener()
        setObservers()
    }

    private fun setListener() {
        binding.apply {
            etPassword.addTextChangedListener {
                viewModel.setPassword(it.toString())
            }
            etPasswordConfirm.addTextChangedListener {
                viewModel.setPasswordConfirm(it.toString())
            }
            binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel.setPasswordStarted()
                }
            }
            etPasswordConfirm.setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    viewModel.setPasswordConfirmStarted()
                }
            }

            btNext.setOnClickListener {
                viewModel.nextScreen()
            }
        }
    }

    private fun setObservers() {
        viewModel.registerData.observe(viewLifecycleOwner) { data ->
            binding.ilPassword.error = null
            binding.ilPasswordConfirm.error = null

            data?.let {

                // Atualização do texto se necessário
                if (it.password.second != null && binding.etPassword.text.toString() != it.password.second) {
                    binding.etPassword.setText(it.password.second)
                }

                if (it.passwordConfirm.second != null && binding.etPasswordConfirm.text.toString() != it.passwordConfirm.second) {
                    binding.etPasswordConfirm.setText(it.passwordConfirm.second)
                }

                viewModel.updatePasswordErrors(it.password.second)

                // Validação da confirmação de senha
                if (data.passwordConfirm.first && it.passwordConfirm.third == ERROR_PASSWORD_CONFIRM_DIFFERENT) {
                    binding.ilPasswordConfirm.error = getString(R.string.error_required_password_confirm_different)
                }

                if(data.password.first && data.password.third == ERROR_PASSWORD_EMPTY){
                    binding.ilPassword.error = getString(R.string.error_required_password)
                }
            }
        }

        viewModel.passwordErrors.observe(viewLifecycleOwner) { errors ->
            Log.d("***Erros", errors.toString())

            if (!errors.contains(ERROR_LESS_THAN_EIGHT)) {
                val color = ContextCompat.getColor(requireContext(), R.color.switch_green)
                binding.ivEightChar.imageTintList = ColorStateList.valueOf(color)
            } else {
                val color = ContextCompat.getColor(requireContext(), R.color.gray_border)
                binding.ivEightChar.imageTintList = ColorStateList.valueOf(color)
            }

            if (!errors.contains(ERROR_MISS_NUMBER)) {
                val color = ContextCompat.getColor(requireContext(), R.color.switch_green)
                binding.ivOneNumber.imageTintList = ColorStateList.valueOf(color)
            } else {
                val color = ContextCompat.getColor(requireContext(), R.color.gray_border)
                binding.ivOneNumber.imageTintList = ColorStateList.valueOf(color)
            }

            if (!errors.contains(ERROR_MISS_SPECIAL_CHAR)) {
                val color = ContextCompat.getColor(requireContext(), R.color.switch_green)
                binding.ivOneSpecialChar.imageTintList = ColorStateList.valueOf(color)
            } else {
                val color = ContextCompat.getColor(requireContext(), R.color.gray_border)
                binding.ivOneSpecialChar.imageTintList = ColorStateList.valueOf(color)
            }
        }

        viewModel.score.observe(viewLifecycleOwner) { score ->
            Log.d("***Score", score.toString())
            binding.pbScore.progress = score

            val strengthConditions = when {
                score > PASSWORD_MEDIUM -> {
                    Triple(
                        R.string.label_password_strong,
                        ContextCompat.getColor(requireContext(), R.color.switch_green),
                        ContextCompat.getColor(requireContext(), R.color.success_light)
                    )
                }
                score > PASSWORD_WEAK -> {
                    Triple(
                        R.string.label_password_weak,
                        ContextCompat.getColor(requireContext(), R.color.component),
                        ContextCompat.getColor(requireContext(), R.color.component)
                    )
                }
                else -> {
                    Triple(
                        R.string.label_password_weak,
                        ContextCompat.getColor(requireContext(), R.color.danger),
                        ContextCompat.getColor(requireContext(), R.color.notification_red)
                    )
                }
            }

            val (tipText, progressColor, textColor) = strengthConditions

            binding.tvPassStrength.setText(tipText)
            binding.pbScore.progressTintList = ColorStateList.valueOf(progressColor)
            binding.tvPassStrength.backgroundTintList = ColorStateList.valueOf(textColor)
        }

        viewModel.alert.observe(viewLifecycleOwner) { content ->
            content?.let {
                Toast.makeText(requireContext(), content, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.goToNextScreen.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), "Sucesso", Toast.LENGTH_SHORT).show()
                viewModel.clearNextScreen()
            }
        }
    }


    private fun makeMessage(listText: List<String>?): String {
        if (listText == null) {
            return getString(R.string.error_fail)
        } else {
            var message = ""
            for (item in listText) {
                /*if (item == ERROR_DATA_NULL) {
                    if (message.isNotBlank()) {
                        message += "\n"
                    }
                    message += getString(R.string.error_data_null)
                }
                if (item == ERROR_NAME_INVALID) {
                    if (message.isNotBlank()) {
                        message += "\n"
                    }
                    message += getString(R.string.error_invalid_full_name)
                }
                if (item == ERROR_NAME_EMPTY) {
                    if (message.isNotBlank()) {
                        message += "\n"
                    }
                    message += getString(R.string.error_required_full_name)
                }*/
            }
            if(message.isBlank() && listText.isNotEmpty()){
                message = listText[0]
            }
            return message
        }
    }

}