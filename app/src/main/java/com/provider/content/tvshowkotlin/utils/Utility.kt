package com.provider.content.tvshowkotlin.utils

import android.text.Html


/**
 * Created by Nand Kishor Patidar on 03,August,2018
 * Email nandkishor.patidar@ratufa.com.
 *
 */
object Utility{
    private val URL_ROOT = "http://api.tvmaze.com/"
    val URL_GET_SHOWS = URL_ROOT + "shows"
    val ID = "id";
    val NAME = "name";
    val TYPE = "type";
    val LANGUAGE = "language";
    val SCHEDULE = "schedule";
    val TIME = "time";
    val DAYS = "days";
    val RATING = "rating";
    val AVERAGE = "average";
    val IMAGE = "image";
    val MEDIUM = "medium";
    val ORIGINAL = "original";
    val SUMMARY = "summary";

    //function for html tags convert into string
    fun stripHtml(html: String) : String{
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        }else
            return Html.fromHtml(html).toString()
    }
}