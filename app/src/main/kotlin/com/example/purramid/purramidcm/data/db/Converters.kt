class Converters {
    @TypeConverter
    fun fromPetType(type: PetType): String = type.name
    
    @TypeConverter
    fun toPetType(type: String): PetType = PetType.valueOf(type)
}