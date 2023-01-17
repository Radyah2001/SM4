package group4.sensimate.presentation.user

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import group4.sensimate.data.model.User
import group4.sensimate.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
): ViewModel()
{
    private val _user = MutableStateFlow(User())
    val userState: StateFlow<User> = _user.asStateFlow()

    fun signUp(): Boolean{
        var created = false
        val user = User(fullname, username,email,password,birthday,gender,postalcode)
        viewModelScope.launch {
            created= userRepository.addUser(user)
        }
        return created
    }

    fun signIn(){
        viewModelScope.launch {
            val response = userRepository.getUser(username)
            if (response != null) {
                _user.value = response
                _user.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true
                    )
                }
            }
        }
    }

    fun signInAsGuest(){
        viewModelScope.launch {
            _user.update { currentState ->
                currentState.copy(
                    username= "Guest",
                    isLoggedIn = true
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            //isLoggedIn = false
        }
    }

    fun getUserInfo(un: String){
        viewModelScope.launch {
            val response = userRepository.getUser(un)
            if(response != null) {
                _user.value = response
                _user.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true
                    )
                }
                fullname = response.fullname
                username = response.username
                email= response.email
                password = response.password
                birthday = response.birthday
                gender = response.gender
                postalcode = response.postalcode
            }
        }
    }

    fun updateUserInfo(): Boolean{
        var isUpdated = false
        val user = User(fullname, username,email,password,birthday,gender,postalcode)
        viewModelScope.launch {
            if(userRepository.updateUser(user)){
                _user.value = user
                isUpdated= true
            }
        }
        return isUpdated
    }

    //--------------------user's form states----------------------------

    var profileImage by mutableStateOf("")
        private set
    var fullname by mutableStateOf("")
        private set
    var username by mutableStateOf("Admin")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var birthday by mutableStateOf("")
        private set
    var gender by mutableStateOf("")
        private set
    var postalcode by mutableStateOf("")
        private set

    var cookieCheckedState by mutableStateOf(true)
        private set
    var ageCheckedState by mutableStateOf(true)
        private set

    //val enableButton= fullname.isNotBlank() && username.isNotBlank() && password.isNotBlank()

    fun profileImageChange(newValue: String){
        profileImage= newValue
    }

    fun fullnameChange(newValue: String){
        fullname= newValue
    }

    fun emailChange(newValue: String){
        email= newValue
    }

    fun usernameChange(newValue: String){
        username= newValue
    }

    fun passwordChange(newValue: String){
        password= newValue
    }

    fun birthdayChange(newValue: String){
        birthday= newValue
    }

    fun genderChange(newValue: String){
        gender = newValue
    }

    fun postalcodeChange(newValue: String){
        postalcode= newValue
    }

    fun cookieCheckedStateChange(newValue: Boolean){
        cookieCheckedState= newValue
    }

    fun ageCheckedStateChange(newValue: Boolean){
        ageCheckedState= newValue
    }
}