package bamboospear.tableshare_server.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),
    @ManyToOne(fetch = FetchType.LAZY)
    val orderer: User,
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product
)
