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