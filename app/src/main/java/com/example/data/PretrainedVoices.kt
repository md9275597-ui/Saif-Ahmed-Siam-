package com.example.data

import com.example.model.VoiceProfile

object PretrainedVoices {

    val masterVoices: List<VoiceProfile> = listOf(
        VoiceProfile(
            id = "news_male_shafiq",
            name = "শফিক আহমেদ (সংবাদ পাঠক)",
            description = "প্রমিত বাংলা টেলিভিশন ও বেতার সংবাদ উপস্থাপক কণ্ঠ। গম্ভীর ও স্পষ্ট উচ্চারণ।",
            gender = "Male",
            isCloned = false,
            isFavorite = true,
            dialectTag = "প্রমিত বাংলা (Standard)",
            similarityScore = 0.98f,
            stabilityScore = 0.95f
        ),
        VoiceProfile(
            id = "news_female_ananya",
            name = "অনন্যা চৌধুরী (সংবাদ পাঠিকা)",
            description = "সুস্পষ্ট ও মার্জিত প্রমিত উচ্চারণ। জাতীয় সংবাদ ও তথ্যচিত্রের জন্য আদর্শ।",
            gender = "Female",
            isCloned = false,
            isFavorite = true,
            dialectTag = "প্রমিত বাংলা (Standard)",
            similarityScore = 0.97f,
            stabilityScore = 0.96f
        ),
        VoiceProfile(
            id = "recite_male_nazrul",
            name = "কাজী নজরুল আবৃত্তি কণ্ঠ",
            description = "গভীর ও বজ্রনির্ঘোষ উদাত্ত কণ্ঠ। কবিতা ও উদ্দীপনামূলক বক্তব্যের জন্য উপযুক্ত।",
            gender = "Male",
            isCloned = false,
            isFavorite = true,
            dialectTag = "কাব্যিক আবৃত্তি",
            similarityScore = 0.96f,
            stabilityScore = 0.90f
        ),
        VoiceProfile(
            id = "recite_male_rabindra",
            name = "রবীন্দ্র আবৃত্তি কণ্ঠ",
            description = "ধীর, ভাবগম্ভীর ও আত্মমগ্ন ধ্রুপদী বাচনভঙ্গি। শান্ত সাহিত্য পাঠের জন্য।",
            gender = "Male",
            isCloned = false,
            isFavorite = false,
            dialectTag = "শান্ত সাহিত্যিক",
            similarityScore = 0.95f,
            stabilityScore = 0.92f
        ),
        VoiceProfile(
            id = "tech_male_siam",
            name = "টেক রিভিউয়ার সিয়াম",
            description = "আধুনিক ও দ্রুতগতির প্রমিত কণ্ঠ। ইউটিউব ভিডিও, পডকাস্ট ও গ্যাজেট পর্যালোচনার জন্য জনপ্রিয়।",
            gender = "Male",
            isCloned = false,
            isFavorite = true,
            dialectTag = "প্রমিত ইউটিউব স্টাইল",
            similarityScore = 0.99f,
            stabilityScore = 0.92f
        ),
        VoiceProfile(
            id = "host_female_tanisha",
            name = "তানিশা (স্মার্ট হোস্ট)",
            description = "মিষ্ট, প্রাণবন্ত ও উষ্ণ বাচনভঙ্গি। বিজ্ঞাপন, অডিওবুক ও কথোপকথনের জন্য মানানসই।",
            gender = "Female",
            isCloned = false,
            isFavorite = true,
            dialectTag = "স্বাভাবিক কথোপকথন",
            similarityScore = 0.96f,
            stabilityScore = 0.93f
        ),
        VoiceProfile(
            id = "story_elderly_dadu",
            name = "গল্পদাদু (লোককাহিনি)",
            description = "স্নেহমাখা প্রবীণ বাচনভঙ্গি। রূপকথা, শিশুদের গল্প ও ঐতিহ্যের উপাখ্যানের জন্য।",
            gender = "Male",
            isCloned = false,
            isFavorite = false,
            dialectTag = "রূপকথা কথক",
            similarityScore = 0.94f,
            stabilityScore = 0.88f
        ),
        VoiceProfile(
            id = "en_male_alex",
            name = "Alex (English Studio Pro)",
            description = "Natural Global English voice for bilingual narration and international projects.",
            gender = "Male",
            isCloned = false,
            isFavorite = false,
            dialectTag = "International English",
            similarityScore = 0.95f,
            stabilityScore = 0.94f
        )
    )
}
