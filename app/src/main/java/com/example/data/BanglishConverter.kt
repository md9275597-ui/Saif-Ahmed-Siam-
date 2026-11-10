package com.example.data

object BanglishConverter {

    private val commonWords = mapOf(
        "ami" to "আমি",
        "tumi" to "তুমি",
        "apni" to "আপনি",
        "se" to "সে",
        "tara" to "তারা",
        "amra" to "আমরা",
        "kemon" to "কেমন",
        "acho" to "আছো",
        "achen" to "আছেন",
        "bhalo" to "ভালো",
        "valo" to "ভালো",
        "bangla" to "বাংলা",
        "bangladesh" to "বাংলাদেশ",
        "dhaka" to "ঢাকা",
        "desh" to "দেশ",
        "bhalobashi" to "ভালোবাসি",
        "valobashi" to "ভালোবাসি",
        "gan" to "গান",
        "shunte" to "শুনতে",
        "korchi" to "করছি",
        "korbo" to "করব",
        "ki" to "কী",
        "keno" to "কেন",
        "kothay" to "কোথায়",
        "kokhon" to "কখন",
        "onek" to "অনেক",
        "khub" to "খুব",
        "sundor" to "সুন্দর",
        "dhonnobad" to "ধন্যবাদ",
        "siam" to "সিয়াম",
        "voice" to "ভয়েস",
        "ai" to "এআই",
        "audio" to "অডিও"
    )

    fun convertBanglishToBangla(input: String): String {
        if (input.isBlank()) return ""
        val tokens = input.split(Regex("(\\s+|[.,!?:;\"'()\\[\\]{}])"))
        var result = input
        for (token in tokens) {
            val clean = token.lowercase().trim()
            if (clean.isNotEmpty()) {
                val bangla = commonWords[clean]
                if (bangla != null) {
                    result = result.replace(Regex("\\b$token\\b", RegexOption.IGNORE_CASE), bangla)
                }
            }
        }
        return result
    }
}
