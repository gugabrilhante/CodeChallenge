package com.arctouch.codechallenge.util

class StringUtils {

    companion object {
        fun getWordsToPhrase(lastWord: String, list: List<String?>): String {
            var text = ""
            for ((index, word) in list.filterNotNull().withIndex()) {
                text += word
                if (index < list.size - 2) text += ", "
                else if (index < list.size - 1) text += " " + lastWord + " "
            }
            return text
        }
    }

}