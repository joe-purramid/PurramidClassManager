@Entity(
    tableName = "pets",
    indices = [Index(value = ["name"], unique = true)]
)
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "name")
    val name: String, // Max 20 characters, supports diacritics
    
    @ColumnInfo(name = "type")
    val type: PetType,
    
    @ColumnInfo(name = "points")
    val points: Int = 0, // Range: 0-99
    
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = false,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

enum class PetType {
    ALIEN, KITTEN, OCTOPUS, PENGUIN, PUPPY, TYRANOSAURUS_REX, UNICORN
}