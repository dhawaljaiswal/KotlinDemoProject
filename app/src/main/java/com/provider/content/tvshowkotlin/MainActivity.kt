package com.provider.content.tvshowkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.error.VolleyError
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.Volley
import com.provider.content.tvshowkotlin.adapters.TvShowAdapter
import com.provider.content.tvshowkotlin.listeners.ListPaddingDecoration
import com.provider.content.tvshowkotlin.models.TvShow
import com.provider.content.tvshowkotlin.utils.Utility
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    private var showList: MutableList<TvShow>? = null
    var requestQueue: RequestQueue? = null

    private var tvShowAdapter: TvShowAdapter? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        showList = mutableListOf<TvShow>()

        getTVShows()

        //create vertical layout manager

        rv_tvShow.layoutManager = LinearLayoutManager(this)
        rv_tvShow.addItemDecoration(ListPaddingDecoration(this@MainActivity, 0, 0))
        tvShowAdapter = TvShowAdapter(showList!!, this)
        rv_tvShow.adapter = tvShowAdapter
        tvShowAdapter?.setOnItemClickListener(object : TvShowAdapter.OnItemClickListener{
            override fun onClick(view: View, position: Int) {
                Toast.makeText(this@MainActivity, "click me at position : $position", Toast.LENGTH_LONG).show()

                val intent = Intent(this@MainActivity, TvShowDetailActivity::class.java)
                intent.putExtra(TV_SHOW, showList?.get(position))
                startActivity(intent)
            }
        })
    }

    fun getTVShows(){

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.GET, Utility.URL_GET_SHOWS,
                Response.Listener<String> { response ->
                    Log.d(TAG, "Response : "+ response)
                    try {
                        val mainJsonArray = org.json.JSONArray(response)
                        if (mainJsonArray.length() > 0 && mainJsonArray != null)
                        {
                            showList?.clear()
                            for ( i in 0..(mainJsonArray.length() -1) ){
                                val obj = mainJsonArray.getJSONObject(i)

                                val tvShow = TvShow()
                                tvShow.id = obj.getInt(Utility.ID)
                                tvShow.name = obj.getString(Utility.NAME)
                                tvShow.type = obj.getString(Utility.TYPE)
                                tvShow.language = obj.getString(Utility.LANGUAGE)

                                //set schedule
                                val scheduleObj = obj.getJSONObject(Utility.SCHEDULE)
                                val daysObj = scheduleObj.getJSONArray(Utility.DAYS)
                                var day = ""
                                if (daysObj.length() > 0){
                                    day = daysObj.get(0).toString()
                                }
                                tvShow.schedule = day + " " + scheduleObj.getString(Utility.TIME)

                                val ratingObj = obj.getJSONObject(Utility.RATING)
                                tvShow.rating = ratingObj.getString(Utility.AVERAGE)

                                val imageObj = obj.getJSONObject(Utility.IMAGE)
                                tvShow.imageMedium = imageObj.getString(Utility.MEDIUM)
                                tvShow.imageOriginal = imageObj.getString(Utility.ORIGINAL)

                                tvShow.summary = obj.getString(Utility.SUMMARY)

                                showList?.add(tvShow)
                                //Toast.makeText(applicationContext, obj.getString(Utility.NAME), Toast.LENGTH_LONG).show()
                                Log.d(TAG, "showList : ${showList.toString()}")
                            }
                            tvShowAdapter?.notifyDataSetChanged()
                        }

                    } catch (e: org.json.JSONException) {
                        e.printStackTrace()
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                /*params.put("name", name)
                params.put("genre", genre)*/
                return params
            }
        }

        //adding request to queue
        AppController.instance?.addToRequestQueue(stringRequest)
    }

    companion object {
        const val TV_SHOW = "tvShow"
    }

}
