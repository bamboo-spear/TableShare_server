package bamboospear.tableshare_server.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),
    val id: String,
    val password: String,
    val name: String,
    val socialCredit: Long = 0,
    val socialCreditStack: Long = 0
) {}