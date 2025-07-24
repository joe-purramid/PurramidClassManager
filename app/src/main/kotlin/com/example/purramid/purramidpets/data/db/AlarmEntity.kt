@Entity(
    tableName = "alarms",
    indices = [Index(value = ["alarm_time"])]
)
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "alarm_time")
    val alarmTime: String, // Format: "HH:mm" (24-hour)
    
    @ColumnInfo(name = "repeat_days")
    val repeatDays: String = "", // Comma-separated: "MON,TUE,WED"
    
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,
    
    @ColumnInfo(name = "sound_uri")
    val soundUri: String? = null, // System alarm sound or music URL
    
    @ColumnInfo(name = "animation")
    val animation: String = "Celebration", // Default animation
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

enum class DayOfWeek {
    MON, TUE, WED, THU, FRI, SAT, SUN
}