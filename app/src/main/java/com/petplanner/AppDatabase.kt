package com.petplanner

import android.content.Context
import androidx.room.*

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val breed: String,
    val age: String,
    val weight: String,
    val summary: String,
    val personality: String,
    val mood: String
)

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val completed: Boolean
)

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val scheduledTime: String,
    val completed: Boolean = false
)

@Dao
interface PetDao {
    @Query("SELECT * FROM pets ORDER BY id DESC LIMIT 1")
    suspend fun getPet(): PetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)
}

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT COUNT(*) FROM tasks WHERE completed = 1")
    suspend fun getCompletedCount(): Int

    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)
}

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE completed = 0")
    suspend fun getUpcomingReminders(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity)
}

@Database(entities = [PetEntity::class, TaskEntity::class, ReminderEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun taskDao(): TaskDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "petplanner.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

fun PetEntity.toPet(): Pet {
    return Pet(
        name = name,
        breed = breed,
        age = age,
        summary = summary,
        personality = personality,
        mood = mood,
        weight = weight
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        completed = completed
    )
}

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = id,
        title = title,
        scheduledTime = scheduledTime,
        completed = completed
    )
}

fun Pet.toEntity(): PetEntity = PetEntity(
    name = name,
    breed = breed,
    age = age,
    weight = weight,
    summary = summary,
    personality = personality,
    mood = mood
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    completed = completed
)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id = id,
    title = title,
    scheduledTime = scheduledTime,
    completed = completed
)
