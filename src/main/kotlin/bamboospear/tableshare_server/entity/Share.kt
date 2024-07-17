package bamboospear.tableshare_server.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Share(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),
    @ManyToOne(fetch = FetchType.LAZY)
    val sharer: User,
    val category: Category,
    @OneToMany(fetch = FetchType.LAZY)
    val images: List<Image>,
    val title: String,
    val description: String,
    val adress: String,
    val lat: Double,
    val lng: Double
)

enum class Category(val value: Int) {
    MEAT(0),
    EGGS_AND_DAIRY(1),
    VEGETABLES(2),
    FISH(3),
    ETC(4);

    companion object {
        private val map = values().associateBy(Category::value)
        fun fromInt(type: Int) = map[type]
    }
}