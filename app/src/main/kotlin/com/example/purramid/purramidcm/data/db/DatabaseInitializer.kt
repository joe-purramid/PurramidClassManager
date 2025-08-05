// Default data insertion strategy
class DatabaseInitializer @Inject constructor(
    private val settingsDao: SettingsDao,
    private val responsiveSettingsDao: ResponsiveSettingsDao,
    private val rewardSettingsDao: RewardSettingsDao
) {
    suspend fun initializeDefaults() {
        // Insert default settings if not exist
        if (settingsDao.getSetting(SettingsKeys.WINDOW_WIDTH) == null) {
            insertDefaultSettings()
        }
        
        // Insert default responsive settings
        if (responsiveSettingsDao.getResponsiveSettingsOnce() == null) {
            responsiveSettingsDao.insertResponsiveSettings(ResponsiveSettings())
        }
        
        // Insert default reward settings  
        if (rewardSettingsDao.getRewardSettingsOnce() == null) {
            rewardSettingsDao.insertRewardSettings(RewardSettings())
        }
    }
}