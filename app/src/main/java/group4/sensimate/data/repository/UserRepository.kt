package group4.sensimate.data.repository

import group4.sensimate.data.model.User


class UserRepository(){
    private val users = mutableListOf<User>(
        User("Marcus James","James","Jamesisdabomb@outlook.com","LeBron","07-06-2000","male","2600"),
        User("Admin", "Admin","user@admin.dk","1234","09-12-1994","female","2700")
    )

    fun getUsers():List<User>{
        return users
    }

    fun getUser(username: String): User? {
        var user = users.find { user: User -> user.username ==username }
        return user
    }

    fun addUser(user :User): Boolean{
        return try {
            users.add(user)
            true
        }catch (e: Exception){
            false
        }
    }

    fun deleteUser(username: String): Boolean{
        return try {
            val user = getUser(username)
            users.remove(user)
            true
        }catch (e: Exception){
            false
        }
    }

    fun updateUser(user : User): Boolean{
        try {
            val u = getUser(user.username)
            if (u != null) {
                u.fullname= user.fullname
                u.password = user.password
                u.email = user.email
                u.birthday= user.birthday
                u.gender= user.gender
                u.postalcode = user.postalcode

                return true
            }
            return false
        }catch (e: Exception){
            return false
        }
    }
}