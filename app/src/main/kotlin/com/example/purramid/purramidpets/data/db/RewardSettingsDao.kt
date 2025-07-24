@Dao
interface RewardSettingsDao {
    @Query("SELECT * FROM reward_settings WHERE id = 1")
    fun getRewardSettings(): Flow<RewardSettings?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRewardSettings(settings: RewardSettings)
    
    @Query("SELECT * FROM reward_settings WHERE id = 1")
    suspend fun getRewardSettingsOnce(): RewardSettings?
}