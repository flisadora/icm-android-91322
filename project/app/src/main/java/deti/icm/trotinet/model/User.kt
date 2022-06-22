package deti.icm.trotinet.model

class User (
    val id: String? = null,
    val name: String = "",
    val email: String = "",
    val balance: Double = 0.0
) {
    fun toUser(id: String): User = User(
        id = id,
        name = name,
        email = email,
        balance = balance
    )
}