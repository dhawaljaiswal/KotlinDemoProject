package com.provider.content.tvshowkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.util.Log
import android.view.Display
import com.bumptech.glide.Glide
import com.provider.content.tvshowkotlin.models.TvShow
import com.provider.content.tvshowkotlin.utils.Utility
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import kotlinx.android.synthetic.main.content_show.*




class TvShowDetailActivity : AppCompatActivity() {
    private val TAG: String = TvShowDetailActivity::class.java.simpleName
    private var imageUrl : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        if (intent != null)
        {
            var tvShowData: TvShow = intent.getSerializableExtra(MainActivity.TV_SHOW) as TvShow

            imageUrl = tvShowData.imageOriginal
            Glide.with(this)
                    .load(imageUrl)
                    .into(fullImage);

            toolbar_layout.title = tvShowData.name
            movieName.text = tvShowData.name
            movieType.text = tvShowData.type
            language.text = tvShowData.language
            schedule.text = tvShowData.schedule
            ratingBarMovie.rating = (tvShowData.rating.toFloat()) / 2
            description.text = Utility.stripHtml(tvShowData.summary)
            Log.d(TAG, tvShowData.name)
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val dWidth : Display = windowManager.defaultDisplay
        fullImage.layoutParams.height = dWidth.width

        //val bitmap = (fullImage.getDrawable() as BitmapDrawable).bitmap
        val bitmap: Bitmap? = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)

        bitmap?.let { Palette.from(it).generate(object : Palette.PaletteAsyncListener{
                override fun onGenerated(palette: Palette?) {
                    val mutedColor : Int? = palette?.getMutedColor(resources.getColor(R.color.colorPrimaryDark))
                    toolbar_layout.setContentScrimColor(mutedColor!!)
                }
            })
        }
    }
}
