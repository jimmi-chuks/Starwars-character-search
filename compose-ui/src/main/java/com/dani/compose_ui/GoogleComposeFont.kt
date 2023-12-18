package com.dani.compose_ui

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font

object GoogleComposeFont {

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )


    val fontName: GoogleFont = GoogleFont("Lobster Two")
    private val roboto: GoogleFont = GoogleFont("Roboto")

    val robotoMedium = Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Medium)
    val robotoItalicMedium = Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Medium, style = FontStyle.Italic)
    val robotoBold = Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Bold)
    val alice = Font(googleFont = GoogleFont("Alice"), fontProvider = provider)
    val anton = Font(googleFont =  GoogleFont("Roboto"), fontProvider = provider)
    val bangers = Font(googleFont = GoogleFont("Bangers"), fontProvider = provider)

    val starWarsFontFamily = FontFamily(robotoBold, robotoMedium, robotoItalicMedium, alice, bangers, anton)
}
