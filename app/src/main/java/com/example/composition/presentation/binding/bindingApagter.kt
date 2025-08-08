package com.example.composition.presentation.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.composition.R

@BindingAdapter("requireAnswer")
fun requireAnswer(textView : TextView, count : Int){
    textView.text = "Необходимое число правильных ответов: $count"
}

@BindingAdapter("requireSmile")
fun requireSmile(imageView: ImageView, winner: Boolean){
    if (winner)
        imageView.setImageResource(R.drawable.ic_smile)
    else
        imageView.setImageResource(R.drawable.ic_sad)
}