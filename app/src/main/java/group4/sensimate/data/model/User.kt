package group4.sensimate.data.model

data class User(
    var fullname : String= "",
    var username : String ="",
    var email : String  ="",
    var password : String  ="",
    var birthday : String  ="",
    var gender : String  ="",
    var postalcode : String  ="",
    var isLoggedIn: Boolean = false
)