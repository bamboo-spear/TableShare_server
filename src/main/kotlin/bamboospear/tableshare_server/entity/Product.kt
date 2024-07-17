package bamboospear.tableshare_server.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val price: Long,
    @OneToOne(fetch = FetchType.LAZY)
    val image: Image
)
