@Entity(
    tableName = "user_settings",
    indices = [Index(value = ["setting_key"], unique = true)]
)
data class UserSetting(
    @PrimaryKey
    @ColumnInfo(name = "setting_key")
    val key: String,
    
    @ColumnInfo(name = "setting_value")
    val value: String,
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

// Settings Keys (stored as constants)
object SettingsKeys {
    const val WINDOW_WIDTH = "window_width" // Default: 300
    const val WINDOW_HEIGHT = "window_height" // Default: 300
    const val SCREEN_POSITION_X = "screen_x" // Default: screen_width - 350
    const val SCREEN_POSITION_Y = "screen_y" // Default: 50
    const val ACTIVE_PET_ID = "active_pet_id"
    const val PET_MODE = "pet_mode" // REWARDS or RESPONSIVE
    const val MUSIC_ENABLED = "music_enabled" // Default: false
    const val MARATHON_ENABLED = "marathon_enabled" // Default: false
    const val POINT_INCREMENT = "point_increment" // Default: 1
}