package ch.creatis.bexio.Room



import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters



@Database(entities = [Contact::class, Projet::class, Temps::class, Tache::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {



    abstract val contactDAO: ContactDAO
    abstract val projetDAO: ProjetDAO
    abstract val tempsDAO: TempsDAO
    abstract val tacheDAO: TachesDAO


    // Vaultlists
    // abstract val categoryannonceDAO: CategoryAnnonceDAO



}