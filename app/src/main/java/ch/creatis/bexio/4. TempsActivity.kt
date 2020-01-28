package ch.creatis.bexio



import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_temps.*
import kotlinx.android.synthetic.main.activity_temps_items.view.*



class TempsActivity : AppCompatActivity() {



    val tableau: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temps)



        tableau.add("deer")
        tableau.add("fox")
        tableau.add("moose")
        tableau.add("buffalo")
        tableau.add("monkey")
        tableau.add("penguin")
        tableau.add("parrot")



        recyclerViewTemps.layoutManager = LinearLayoutManager(this)
        recyclerViewTemps.adapter = TempsAdapter(tableau, this)



    }



}



class TempsAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<TempsHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempsHolder {
        return TempsHolder(
            LayoutInflater.from(context).inflate(
                R.layout.activity_temps_items,
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: TempsHolder, position: Int) {
        holder.viewTemps?.text = items.get(position)
    }



    override fun getItemCount(): Int {
        return items.size
    }



}



class TempsHolder (view: View) : RecyclerView.ViewHolder(view) {

    val viewTemps = view.semaineLabel

}