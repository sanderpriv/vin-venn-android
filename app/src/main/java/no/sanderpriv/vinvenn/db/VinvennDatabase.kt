package no.sanderpriv.vinvenn.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import no.sanderpriv.vinvenn.api.MealsDto
import java.util.Date

@Database(
    version = 1,
    entities = [
        MealsDb::class,
    ]
)
@TypeConverters(value = [DateSerializer::class, DtoSerializer::class])
abstract class VinVennDatabase : RoomDatabase() {
    abstract fun vinVennCacheDao(): VinVennCacheDao

    companion object{
        fun buildDb(context: Context): VinVennDatabase =
            Room.databaseBuilder(context, VinVennDatabase::class.java, "vinvenn-db").build()

        const val MEALS_TABLE_NAME = "meals"
    }
}

@Dao
interface VinVennCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: MealsDb)

    @Query("SELECT * FROM ${VinVennDatabase.MEALS_TABLE_NAME}")
    suspend fun getMealsDb(): MealsDb?
}

@Entity(tableName = VinVennDatabase.MEALS_TABLE_NAME)
data class MealsDb(
    @PrimaryKey
    val id: String = "id",
    val dto: MealsDto,
    val expirationDate: Date,
)

class DtoSerializer {
    val gson = Gson()

    @TypeConverter
    fun serializeMealsDto(dto: MealsDto): String = gson.toJson(dto)

    @TypeConverter
    fun deserializeMealsDto(json: String): MealsDto = gson.fromJson(json, MealsDto::class.java)
}

class DateSerializer {

    @TypeConverter
    fun fromDate(date: Date) : Long = date.time

    @TypeConverter
    fun toDate(time: Long) : Date = Date(time)
}
