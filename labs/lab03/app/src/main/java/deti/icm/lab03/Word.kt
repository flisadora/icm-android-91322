package deti.icm.lab03

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
@Entity(tableName = "word_table"):
Each @Entity class represents a SQLite table. Annotate your class declaration to indicate that it's an entity. You can specify the name of the table if you want it to be different from the name of the class. This names the table "word_table".

@PrimaryKey:
Every entity needs a primary key. To keep things simple, each word acts as its own primary key.

@ColumnInfo(name = "word"):
Specifies the name of the column in the table if you want it to be different from the name of the member variable. This names the column "word".

Every property that's stored in the database needs to have public visibility, which is the Kotlin default.

https://developer.android.com/codelabs/android-room-with-a-view-kotlin#4
*/


@Entity(tableName = "word_table")
class Word(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "word") val word: String
)