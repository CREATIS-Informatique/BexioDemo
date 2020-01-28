package ch.creatis.bexio

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_projets.*
import kotlinx.android.synthetic.main.activity_projets_items.view.*
import org.json.JSONArray

class ProjetsActivity : AppCompatActivity() {


    // -----------------------------------

    private var numberOfRequestsToMake = 0
    private var hasRequestFailed = false

    // -----------------------------------


    val tableau: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projets)



        tableau.add("Application")
        tableau.add("Application")
        tableau.add("Application")
        tableau.add("Application")
        tableau.add("Application")
        tableau.add("Application")
        tableau.add("Application")



        recyclerViewProjets.layoutManager = LinearLayoutManager(this)
        recyclerViewProjets.adapter = ProjetsAdapter(tableau, this)

        RefreshRequest()

    }




            fun RefreshRequest(){
//
//            val database = Room.databaseBuilder(this, AppDatabase::class.java, "mydb").allowMainThreadQueries().build()
//            val contactDAO = database.contactDAO
//            contactDAO.delete()
//
//            // -----------------------------------------------------------------------------------------
//
            val sharedPreferences = this.getSharedPreferences("Bexio", Context.MODE_PRIVATE)
            val org = sharedPreferences.getString("ORG", "")
            val url = "https://office.bexio.com/api2.php/$org/pr_project"
            val accessToken = sharedPreferences.getString("ACCESSTOKEN", "")

//            // -----------------------------------------------------------------------------------------



            val queue = Volley.newRequestQueue(this)
            val stringRequest = object : JsonArrayRequest(Method.GET, url,JSONArray(), Response.Listener<JSONArray> { response ->

                println(response)


                for (i in 0 until response.length()) {

                }







                numberOfRequestsToMake--
                if (numberOfRequestsToMake == 0) { requestEndInternet() }



            }, Response.ErrorListener {

                numberOfRequestsToMake--
                hasRequestFailed = true
                if (numberOfRequestsToMake == 0) { requestEndInternet() }

            })

            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Authorization"] = "Bearer $accessToken"
                    return headers
                }
            }



            queue.add(stringRequest)
            numberOfRequestsToMake++



        }


    // -------------------------------------------------------------------------------------- Internet ---------------------------------------------------------------------------------------

    fun isConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }



    fun requestEndInternet() {
        if (hasRequestFailed) {
            hasRequestFailed = false

        } else {
//            val database = Room.databaseBuilder(this, AppDatabase::class.java, "mydb").allowMainThreadQueries().build()
//            val contactDAO = database.contactDAO
//            contactList = contactDAO.getItems() as ArrayList<Contact>
//            adapter = ContactAdapter(this@ContactsActivity, contactList)
//            GridContacts.adapter = adapter
//            refreshView.isRefreshing = false
        }

    }



    fun Alerte(){

//        refreshView.isRefreshing = false
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Aucune connexion à internet")
        builder.setMessage("Vérifiez vos réglages avant de pouvoir utiliser l'application.")
        builder.setPositiveButton("Ok"){dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    // -------------------------------------------------------------------------------------- Internet ---------------------------------------------------------------------------------------



}



class ProjetsAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ProjetsHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjetsHolder {
        return ProjetsHolder(
            LayoutInflater.from(context).inflate(
                R.layout.activity_projets_items,
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: ProjetsHolder, position: Int) {
        holder.viewTemps?.text = items.get(position)
    }



    override fun getItemCount(): Int {
        return items.size
    }



}



class ProjetsHolder (view: View) : RecyclerView.ViewHolder(view) {

    val viewTemps = view.projetLabel

}