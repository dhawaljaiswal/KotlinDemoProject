package com.provider.content.tvshowkotlin.models

import java.io.Serializable

/**
 * Created by Nand Kishor Patidar on 03,August,2018
 * Email nandkishor.patidar@ratufa.com.
 *
 */
class TvShow : Serializable {
    var id: Int? = 0
    var name: String = ""
    var type: String = ""
    var language: String = ""
    var schedule: String = ""
    var rating: String = ""
    var imageMedium: String = ""
    var imageOriginal: String = ""
    var summary: String = ""
}