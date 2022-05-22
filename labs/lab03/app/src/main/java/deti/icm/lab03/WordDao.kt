package deti.icm.lab03

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/*
In the DAO (data access object), you specify SQL queries and associate them with method calls.
The compiler checks the SQL and generates queries from convenience annotations for common queries, such as @Insert.
Room uses the DAO to create a clean API for your code.

The DAO must be an interface or abstract class.

By default, all queries must be executed on a separate thread.

https://developer.android.com/codelabs/android-room-with-a-view-kotlin#5
*/


@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}