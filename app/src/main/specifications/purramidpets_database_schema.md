# Purramid Pets - Database Schema

## Database Overview

**Database Name**: `purramid_pets_db`  
**ORM Framework**: Room Database  
**Database Version**: 1  
**Migration Strategy**: Hybrid - Destructive in DEBUG builds, proper migrations in RELEASE builds

## Entity Definitions

### **Pet Entity**
```kotlin
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
    ALIEN, CARDINAL, KITTEN, OCTOPUS, PUPPY, TYRANOSAURUS_REX, UNICORN
}
```

### **User Settings Entity**
```kotlin
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
```

### **Responsive Settings Entity**
```kotlin
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
```

### **Reward Settings Entity**
```kotlin
@Entity(tableName = "reward_settings")
data class RewardSettings(
    @PrimaryKey
    val id: Int = 1, // Single row table
    
    @ColumnInfo(name = "bicycle_jump_cost")
    val bicycleJumpCost: Int = 1,
    
    @ColumnInfo(name = "celebration_cost")
    val celebrationCost: Int = 1,
    
    @ColumnInfo(name = "dance_party_cost")
    val dancePartyCost: Int = 1,
    
    @ColumnInfo(name = "rave_cost")
    val raveCost: Int = 1,
    
    @ColumnInfo(name = "treats_cost")
    val treatsCost: Int = 1,
    
    @ColumnInfo(name = "tricks_cost")
    val tricksCost: Int = 1,
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
```

### **Music URL Entity**
```kotlin
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
```

### **Alarm Entity**
```kotlin
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
```

## DAO Interfaces

### **Pet DAO**
```kotlin
@Dao
interface PetDao {
    @Query("SELECT * FROM pets ORDER BY name ASC")
    fun getAllPets(): Flow<List<Pet>>
    
    @Query("SELECT * FROM pets WHERE is_active = 1 LIMIT 1")
    fun getActivePet(): Flow<Pet?>
    
    @Query("SELECT * FROM pets WHERE id = :petId")
    suspend fun getPetById(petId: Long): Pet?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet): Long
    
    @Update
    suspend fun updatePet(pet: Pet)
    
    @Query("UPDATE pets SET is_active = 0")
    suspend fun deactivateAllPets()
    
    @Query("UPDATE pets SET is_active = 1 WHERE id = :petId")
    suspend fun setActivePet(petId: Long)
    
    @Query("UPDATE pets SET points = :points WHERE id = :petId")
    suspend fun updatePetPoints(petId: Long, points: Int)
    
    @Delete
    suspend fun deletePet(pet: Pet)
    
    @Query("SELECT COUNT(*) FROM pets")
    suspend fun getPetCount(): Int
}
```

### **Settings DAO**
```kotlin
@Dao
interface SettingsDao {
    @Query("SELECT * FROM user_settings WHERE setting_key = :key")
    suspend fun getSetting(key: String): UserSetting?
    
    @Query("SELECT setting_value FROM user_settings WHERE setting_key = :key")
    suspend fun getSettingValue(key: String): String?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: UserSetting)
    
    @Transaction
    suspend fun updateSetting(key: String, value: String) {
        insertSetting(UserSetting(key, value))
    }
    
    @Query("SELECT * FROM user_settings")
    fun getAllSettings(): Flow<List<UserSetting>>
}
```

### **Responsive Settings DAO**
```kotlin
@Dao
interface ResponsiveSettingsDao {
    @Query("SELECT * FROM responsive_settings WHERE id = 1")
    fun getResponsiveSettings(): Flow<ResponsiveSettings?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponsiveSettings(settings: ResponsiveSettings)
    
    @Query("SELECT * FROM responsive_settings WHERE id = 1")
    suspend fun getResponsiveSettingsOnce(): ResponsiveSettings?
}
```

### **Reward Settings DAO**
```kotlin
@Dao
interface RewardSettingsDao {
    @Query("SELECT * FROM reward_settings WHERE id = 1")
    fun getRewardSettings(): Flow<RewardSettings?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRewardSettings(settings: RewardSettings)
    
    @Query("SELECT * FROM reward_settings WHERE id = 1")
    suspend fun getRewardSettingsOnce(): RewardSettings?
}
```

### **Music URL DAO**
```kotlin
@Dao
interface MusicUrlDao {
    @Query("SELECT * FROM music_urls ORDER BY last_used DESC LIMIT 3")
    fun getRecentMusicUrls(): Flow<List<MusicUrl>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusicUrl(musicUrl: MusicUrl): Long
    
    @Query("UPDATE music_urls SET last_used = :timestamp WHERE url = :url")
    suspend fun updateLastUsed(url: String, timestamp: Long = System.currentTimeMillis())
    
    @Query("DELETE FROM music_urls WHERE id NOT IN (SELECT id FROM music_urls ORDER BY last_used DESC LIMIT 3)")
    suspend fun cleanupOldUrls()
    
    @Query("UPDATE music_urls SET is_valid = :isValid WHERE url = :url")
    suspend fun updateUrlValidity(url: String, isValid: Boolean)
}
```

### **Alarm DAO**
```kotlin
@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms ORDER BY alarm_time ASC")
    fun getAllAlarms(): Flow<List<Alarm>>
    
    @Query("SELECT * FROM alarms WHERE is_active = 1")
    suspend fun getActiveAlarms(): List<Alarm>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm): Long
    
    @Update
    suspend fun updateAlarm(alarm: Alarm)
    
    @Delete
    suspend fun deleteAlarm(alarm: Alarm)
    
    @Query("SELECT COUNT(*) FROM alarms")
    suspend fun getAlarmCount(): Int
    
    @Query("UPDATE alarms SET is_active = :isActive WHERE id = :alarmId")
    suspend fun updateAlarmActive(alarmId: Long, isActive: Boolean)
}
```

## Database Class

```kotlin
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
```

## Type Converters

```kotlin
class Converters {
    @TypeConverter
    fun fromPetType(type: PetType): String = type.name
    
    @TypeConverter
    fun toPetType(type: String): PetType = PetType.valueOf(type)
}
```

## Database Module (Hilt)

```kotlin
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
```

## Initial Data Population

```kotlin
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
```

## Performance Considerations

### **Query Optimization**
- Indexes on frequently queried columns (name, alarm_time, is_active)
- LIMIT clauses for large result sets
- Flow-based reactive queries for UI updates

### **Memory Management**
- Lazy loading for pet animations
- Cleanup of old music URLs (keep only 3 recent)
- Efficient bitmap handling for pet graphics

### **Data Constraints**
- Pet names: 20 character limit
- Points range: 0-99
- Maximum 8 pets per user
- Maximum 8 alarms per user
- Music URLs: Keep only 3 most recent