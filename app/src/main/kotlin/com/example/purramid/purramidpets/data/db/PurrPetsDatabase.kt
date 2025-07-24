@Database(
    entities = [
        Pet::class,
        UserSetting::class,
        ResponsiveSettings::class,
        RewardSettings::class,
        MusicUrl::class,
        Alarm::class
    ],
    version = 1,
    exportSchema = true // Enable schema export for migration tracking
)
@TypeConverters(Converters::class)
abstract class PurrPetsDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun settingsDao(): SettingsDao
    abstract fun responsiveSettingsDao(): ResponsiveSettingsDao
    abstract fun rewardSettingsDao(): RewardSettingsDao
    abstract fun musicUrlDao(): MusicUrlDao
    abstract fun alarmDao(): AlarmDao
    
    companion object {
        // Migration framework ready for future schema changes
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Future migrations will be implemented here
            }
        }
    }
}