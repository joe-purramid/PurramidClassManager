@Entity(tableName = "responsive_settings")
data class ResponsiveSettings(
    @PrimaryKey
    val id: Int = 1, // Single row table
    
    @ColumnInfo(name = "green_threshold")
    val greenThreshold: Int = 59, // dB or less
    
    @ColumnInfo(name = "green_animation")
    val greenAnimation: String = "Sleep",
    
    @ColumnInfo(name = "yellow_min")
    val yellowMin: Int = 60, // dB range start
    
    @ColumnInfo(name = "yellow_max")
    val yellowMax: Int = 79, // dB range end
    
    @ColumnInfo(name = "yellow_animation")
    val yellowAnimation: String = "Ear plugs",
    
    @ColumnInfo(name = "red_threshold")
    val redThreshold: Int = 80, // dB or more
    
    @ColumnInfo(name = "red_animation")
    val redAnimation: String = "Crying"
)

// Available animations for all levels
enum class PetAnimation {
    COWER, CRY, EAR_PLUGS, HAPPY, PRANCE, SLEEP
}