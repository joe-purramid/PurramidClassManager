@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PurrPetsDatabase {
        return Room.databaseBuilder(
            context,
            PurrPetsDatabase::class.java,
            "purramid_pets_db"
        ).apply {
            if (BuildConfig.DEBUG) {
                // Development: Allow destructive migration for faster iteration
                fallbackToDestructiveMigration()
            } else {
                // Production: Require proper migrations to preserve user data
                addMigrations(
                    PurrPetsDatabase.MIGRATION_1_2
                    // Add future migrations here
                )
            }
        }.build()
    }
    
    @Provides
    fun providePetDao(database: PurrPetsDatabase): PetDao = database.petDao()
    
    @Provides
    fun provideSettingsDao(database: PurrPetsDatabase): SettingsDao = database.settingsDao()
    
    @Provides
    fun provideResponsiveSettingsDao(database: PurrPetsDatabase): ResponsiveSettingsDao = 
        database.responsiveSettingsDao()
    
    @Provides
    fun provideRewardSettingsDao(database: PurrPetsDatabase): RewardSettingsDao = 
        database.rewardSettingsDao()
    
    @Provides
    fun provideMusicUrlDao(database: PurrPetsDatabase): MusicUrlDao = database.musicUrlDao()
    
    @Provides
    fun provideAlarmDao(database: PurrPetsDatabase): AlarmDao = database.alarmDao()
}