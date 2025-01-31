package com.example.vitesseapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun callCandidate(number: Int, context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    context.startActivity(intent)
}

fun messageCandidate(number: Int, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("smsto:$number")
    context.startActivity(intent)
}

fun emailCandidate(email: String, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:$email")
    context.startActivity(intent)
}