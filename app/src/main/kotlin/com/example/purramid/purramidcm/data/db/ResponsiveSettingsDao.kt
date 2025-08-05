@Dao
interface ResponsiveSettingsDao {
    @Query("SELECT * FROM responsive_settings WHERE id = 1")
    fun getResponsiveSettings(): Flow<ResponsiveSettings?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponsiveSettings(settings: ResponsiveSettings)
    
    @Query("SELECT * FROM responsive_settings WHERE id = 1")
    suspend fun getResponsiveSettingsOnce(): ResponsiveSettings?
}