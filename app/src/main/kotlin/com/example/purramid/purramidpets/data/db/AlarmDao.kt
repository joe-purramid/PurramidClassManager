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