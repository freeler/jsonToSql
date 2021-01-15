package com.freeler.jsonconvert

import org.litepal.crud.LitePalSupport

class Tiku : LitePalSupport() {

    var question: String? = null
    var answer: String? = null
    var wrongAnswer: String = ""
}