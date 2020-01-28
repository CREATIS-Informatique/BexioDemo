package ch.creatis.bexio.Room



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
interface ContactDAO {



    @Query("DELETE FROM contacts")
    fun delete()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Contact)



    @Query("SELECT * FROM contacts ORDER BY name_un DESC")
    fun getItems(): List<Contact>



    // @Query("SELECT * FROM contacts WHERE type == :type ORDER BY nom")
    // fun getItemsByType(type: String): List<Contact>



}



@Dao
interface ProjetDAO {



    @Query("DELETE FROM projets")
    fun delete()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Projet)



    @Query("SELECT * FROM projets ORDER BY name DESC")
    fun getItems(): List<Projet>



    // @Query("SELECT * FROM contacts WHERE type == :type ORDER BY nom")
    // fun getItemsByType(type: String): List<Projet>



}



@Dao
interface TempsDAO {



    @Query("DELETE FROM temps")
    fun delete()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Temps)



    @Query("SELECT * FROM temps ORDER BY idBexio DESC")
    fun getItems(): List<Temps>



    // @Query("SELECT * FROM contacts WHERE type == :type ORDER BY nom")
    // fun getItemsByType(type: String): List<Temps>



}



@Dao
interface TachesDAO {



    @Query("DELETE FROM taches")
    fun delete()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Tache)



    @Query("SELECT * FROM taches ORDER BY idBexio DESC")
    fun getItems(): List<Tache>



    // @Query("SELECT * FROM taches WHERE type == :type ORDER BY nom")
    // fun getItemsByType(type: String): List<Tache>



}