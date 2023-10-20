package com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterPasswordViewModel : ViewModel() {

    private var _registerData = MutableLiveData<TwoWheelsData?>()
    val registerData : LiveData<TwoWheelsData?> = _registerData

    private var _nextScreen = MutableLiveData<TwoWheelsData?>()
    val goToNextScreen : LiveData<TwoWheelsData?> = _nextScreen

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    private var _alert = MutableLiveData<String?>()
    val alert : LiveData<String?> = _alert


    private val _passwordErrors = MutableLiveData<List<String>>()
    val passwordErrors: LiveData<List<String>> = _passwordErrors


    fun setInitRegisterData(twoWheelsData: TwoWheelsData) {
        _registerData.value = twoWheelsData
    }

    private fun existRegisterData() : Boolean = _registerData.value != null

    fun setPassword(password: String) {
        if (existRegisterData()) {
            val data = registerData.value!!
            data.password = Triple(false, password, "")
            _registerData.value = data
        }
    }

    fun updatePasswordErrors(password: String?) {
        if (!password.isNullOrBlank()){
            _passwordErrors.value = validatePassword(password)
        }
    }

    fun setPasswordConfirm(password: String) {
        if(existRegisterData()){
            val data = registerData.value!!
            data.passwordConfirm = Triple(false, password, validatePasswordConfirm(password))
            _registerData.value = data
        }
    }

    fun setPasswordStarted() {
        if(existRegisterData()){
            val data = registerData.value!!
            data.password = Triple(true, data.password.second, data.password.third)
            _registerData.value = data
        }
    }
    fun initializeErrors() {
        val initialErrors = mutableListOf(ERROR_LESS_THAN_EIGHT, ERROR_MISS_NUMBER, ERROR_MISS_SPECIAL_CHAR)
        val data = registerData.value!!  // Supondo que RegisterData seja o nome do seu data class
        data.password = Triple(true, "", initialErrors.joinToString())
        _registerData.value = data
    }


    fun setPasswordConfirmStarted() {
        if(existRegisterData()){
            val data = registerData.value!!
            data.passwordConfirm = Triple(true, data.passwordConfirm.second, data.passwordConfirm.third)
            _registerData.value = data
        }
    }

    private fun validatePasswordConfirm(password: String?): String? {
        return if(registerData.value!!.password.second != password){
            ERROR_PASSWORD_CONFIRM_DIFFERENT
        } else
            ""
    }


    private fun validatePassword(password: String?): List<String> {
        val errors = mutableListOf<String>()
        if (password.isNullOrBlank()){
            errors.add(ERROR_PASSWORD_EMPTY)
        }
        if (password != null){
            if (!checkOneNumber(password)) {
                errors.add(ERROR_MISS_NUMBER)
            }
            if (!checkEightCharacter(password)) {
                errors.add(ERROR_LESS_THAN_EIGHT)
            }
            if (!checkSymbol(password)) {
                errors.add(ERROR_MISS_SPECIAL_CHAR)
            }
        }
        calculateScore(errors.size)
        return errors
    }

    private fun calculateScore(size: Int) {
        _score.value = if (size >= 3){
            0
        }else if (size == 2){
            PASSWORD_WEAK
        }else if (size == 1){
            PASSWORD_MEDIUM
        }else{
            PASSWORD_STRONG
        }
    }


    fun nextScreen() {
        if(isValid()){
            _nextScreen.value = registerData.value
        }
    }

    private fun isValid() : Boolean {
        var valid = true
        val errors = mutableListOf<String>()

        if (existRegisterData()) {
            val data = registerData.value!!

            val passwordErrors = validatePassword(data.password.second)
            val passwordConfirmErrors = validatePasswordConfirm(data.passwordConfirm.second)

            // Ajustar para pegar o primeiro erro de cada lista, se houver
            val passwordErrorString: String? = passwordErrors.firstOrNull()
            val passwordConfirmErrorString: String? = passwordConfirmErrors

            data.password = Triple(true, data.password.second, passwordErrorString)
            data.passwordConfirm = Triple(true, data.passwordConfirm.second, passwordConfirmErrorString)
            _registerData.value = data

            if(!passwordErrorString.isNullOrBlank()){
                errors.add(passwordErrorString)
                valid = false
            }

            if(!passwordConfirmErrorString.isNullOrBlank()){
                errors.add(passwordConfirmErrorString)
                valid = false
            }

        } else {
            errors.add(ERROR_DATA_NULL)
            valid = false
        }

        if(!valid){
            _alert.value = TITLE_FAIL
        }

        return valid
    }


    fun clearAlert() {
        _alert.value = null
    }

    fun clearNextScreen() {
        _nextScreen.value = null
    }

    private fun checkOneNumber(password: String): Boolean {
        return password.contains(Regex("[0-9]"))
    }

    private fun checkSymbol(password: String): Boolean {
        return password.contains(Regex("[-_+*/|!@#$%&()?ˆ]"))
    }


    private fun checkEightCharacter(password: String): Boolean {
        return password.length >= 8
    }


    companion object {
        const val TITLE_FAIL = "fail"
        const val TITLE_VALIDATE = "no_validate"
        const val ERROR_DATA_NULL = "error_data_null"
        const val ERROR_PASSWORD_EMPTY = "error_password_empty"
        const val ERROR_PASSWORD_INVALID = "error_password_invalid"
        const val ERROR_PASSWORD_CONFIRM_DIFFERENT = "error_password_confirm_different"
        const val ERROR_LESS_THAN_EIGHT = "error_less_than_eight"
        const val ERROR_MISS_SPECIAL_CHAR = "error_miss_special_char"
        const val ERROR_MISS_NUMBER = "error_missNumber"
        const val PASSWORD_WEAK = 30
        const val PASSWORD_MEDIUM = 60
        const val PASSWORD_STRONG = 90
    }
}