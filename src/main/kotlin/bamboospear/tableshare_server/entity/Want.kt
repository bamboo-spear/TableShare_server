package bamboospear.tableshare_server.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Want(
    @Id
    @GeneratedValue
    val uuid: UUID = UUID.randomUUID(),
    @ManyToOne(fetch = FetchType.LAZY)
    val wisher: User,
    @ManyToOne(fetch = FetchType.LAZY)
    val share: Share
)
