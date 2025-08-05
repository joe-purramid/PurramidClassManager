@Entity(
    tableName = "music_urls",
    indices = [Index(value = ["url"], unique = true)]
)
data class MusicUrl(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "url")
    val url: String,
    
    @ColumnInfo(name = "title")
    val title: String? = null, // Extracted from URL if available
    
    @ColumnInfo(name = "last_used")
    val lastUsed: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "is_valid")
    val isValid: Boolean = true // For URL validation caching
)