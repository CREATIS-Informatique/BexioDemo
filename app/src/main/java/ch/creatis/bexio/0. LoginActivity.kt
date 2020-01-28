package ch.creatis.bexio



import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.room.Room
import ch.creatis.bexio.Room.AppDatabase
import ch.creatis.bexio.Room.Contact
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject



class LoginActivity : AppCompatActivity() {



    private var numberOfRequestsToMake = 0
    private var hasRequestFailed = false
    var contactListDatabase = mutableListOf<Contact>()



    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val sharedPreferences = getSharedPreferences("Bexio", Context.MODE_PRIVATE)
        var refreshToken = sharedPreferences.getString("REFRESHTOKEN", "")
        if (refreshToken == ""){ webViewIsVisible() } else {
            getAccessTokenAllTime()
        }



    }



    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------















    // 1
    fun webViewIsVisible(){

        val sharedPreferences = getSharedPreferences("Bexio", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()



        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {



            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (url!!.contains("code")) {



                    val chars = url
                    val codeun = chars!!.dropLast(18)



                    // -------------------------------------------------------------------------------------

                        editor.putString("CODETOKEN", codeun.drop(16))
                        editor.commit()
                        getAccessTokenFirstTime()

                    // -------------------------------------------------------------------------------------



                }
            }
        }



        webView.loadUrl("https://office.bexio.com/oauth/authorize?client_id=0000000000&redirect_uri=00000://00000&state=0000000000&scope=contact_show project_show task_show monitoring_edit")



    }



    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // 2
    fun getAccessTokenFirstTime(){

        val sharedPreferences = getSharedPreferences("Bexio", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var codeToken = sharedPreferences.getString("CODETOKEN", "")



        val queue = Volley.newRequestQueue(this)

        val url = "https://office.bexio.com/oauth/access_token?client_id=0000000000&redirect_uri=0000000000&client_secret=0000000000&$codeToken"

        val stringRequest = StringRequest(Request.Method.POST, url, Response.Listener<String> { response ->

            var responseJsonObj = JSONObject(response)


            // -------------------------------------------------------------------------------------

            webView.visibility = View.INVISIBLE
            editor.putString("ACCESSTOKEN", responseJsonObj.getString("access_token"))
            editor.putString("REFRESHTOKEN", responseJsonObj.getString("refresh_token"))
            editor.putString("ORG", responseJsonObj.getString("org"))
            editor.commit()
            makeAllDataRequest()

            // -------------------------------------------------------------------------------------



        }, Response.ErrorListener {})

        queue.add(stringRequest)



    }















    // Last
    fun getAccessTokenAllTime(){

        val sharedPreferences = getSharedPreferences("Bexio", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var refreshToken = sharedPreferences.getString("REFRESHTOKEN", "")



        val queue = Volley.newRequestQueue(this)

        val url = "https://office.bexio.com/oauth/refresh_token?client_id=0000000000&client_secret=0000000000&refresh_token=$refreshToken"

        val stringRequest = StringRequest(Request.Method.POST, url, Response.Listener<String> { response ->



            // -------------------------------------------------------------------------------------

            var responseJsonObj = JSONObject(response)
            editor.putString("ACCESSTOKEN", responseJsonObj.getString("access_token"))
            editor.putString("REFRESHTOKEN", responseJsonObj.getString("refresh_token"))
            editor.putString("ORG", responseJsonObj.getString("org"))
            editor.commit()



            makeAllDataRequest()

            // -------------------------------------------------------------------------------------



        }, Response.ErrorListener {})

        queue.add(stringRequest)



    }



    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // 3
    fun makeAllDataRequest(){



        // -----------------------------------------------------------------------------------------

        val sharedPreferences = this.getSharedPreferences("Bexio", Context.MODE_PRIVATE)
        val org = sharedPreferences.getString("ORG", "")
        val url = "https://office.bexio.com/api2.php/$org/contact"
        val accessToken = sharedPreferences.getString("ACCESSTOKEN", "")

        // -----------------------------------------------------------------------------------------



        val queue = Volley.newRequestQueue(this)
        val stringRequest = object : JsonArrayRequest(Method.GET, url, JSONArray(), Response.Listener<JSONArray> { response ->

                for (i in 0 until response.length()) {
                    val idBexio= response.getJSONObject(i)["id"].toString()
                    val name_un= response.getJSONObject(i)["name_1"].toString()
                    val name_deux= response.getJSONObject(i)["name_2"].toString()
                    val address= response.getJSONObject(i)["address"].toString()
                    val postcode= response.getJSONObject(i)["postcode"].toString()
                    val city= response.getJSONObject(i)["city"].toString()
                    val mail= response.getJSONObject(i)["mail"].toString()
                    val phone_fixed= response.getJSONObject(i)["phone_fixed"].toString()
                    val contact = Contact(null, idBexio,name_un,name_deux,address,postcode,city,mail,phone_fixed)
                    contactListDatabase.add(contact)
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


    // 4
    fun requestEndInternet() {

        if (hasRequestFailed) {
            hasRequestFailed = false


        } else {

            updateDatabase()

        }



    }


    // 5
    fun updateDatabase(){



        val database = Room.databaseBuilder(this, AppDatabase::class.java, "mydb").fallbackToDestructiveMigration().allowMainThreadQueries().build()



        val contactDAO = database.contactDAO
        contactDAO.delete()
        for (contact in contactListDatabase){ contactDAO.insert(contact)}



        val intentAct = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intentAct)



    }



}