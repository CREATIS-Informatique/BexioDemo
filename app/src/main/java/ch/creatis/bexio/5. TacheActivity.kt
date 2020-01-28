package ch.creatis.bexio

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_taches.*
import kotlinx.android.synthetic.main.activity_taches_items.view.*





class TacheActivity : AppCompatActivity() {




    val tableau: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taches)



        tableau.add("30.11.2019 23:15")
        tableau.add("30.11.2019 23:15")
        tableau.add("30.11.2019 23:15")
        tableau.add("30.11.2019 23:15")
        tableau.add("30.11.2019 23:15")
        tableau.add("30.11.2019 23:15")
        tableau.add("30.11.2019 23:15")



        recyclerViewTaches.layoutManager = LinearLayoutManager(this)
        recyclerViewTaches.adapter = TachesAdapter(tableau, this)



    }



}



class TachesAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<TachesHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TachesHolder {
        return TachesHolder(LayoutInflater.from(context).inflate(R.layout.activity_taches_items, parent, false))
    }



    override fun onBindViewHolder(holder: TachesHolder, position: Int) {
        holder.viewTaches.text = items.get(position)
    }



    override fun getItemCount(): Int {
        return items.size
    }



}



class TachesHolder (view: View) : RecyclerView.ViewHolder(view) {

    val viewTaches = view.dateLimiteLabel

}