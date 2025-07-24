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