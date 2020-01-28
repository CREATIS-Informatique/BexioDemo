package ch.creatis.bexio



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.os.Build
import kotlinx.android.synthetic.main.activity_a_main.*







class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_main)



        TransparentTopBar()



        // Contacts
        ContactsFrame.setOnClickListener {

            val intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }



        // Projets

        ProjetsFrame.setOnClickListener {

            val intent = Intent(this, ProjetsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }



        // Temps

        TempsFrame.setOnClickListener {

            val intent = Intent(this, TempsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }



        // Tâches

        TachesFrame.setOnClickListener {

            val intent = Intent(this, TacheActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }



        // Réglages

        ReglagesFrame.setOnClickListener {

            val intent = Intent(this, ReglagesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }



    }



    // Disparition de la TopBar

    fun TransparentTopBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

    }



    override fun onBackPressed() {
        return
    }



}
