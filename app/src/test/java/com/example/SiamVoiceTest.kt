package com.example

import com.example.data.BanglishConverter
import com.example.data.DistrictDialectData
import com.example.data.PretrainedVoices
import com.example.data.TextChunker
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SiamVoiceTest {

    @Test
    fun testAll64DistrictsPresent() {
        val allDistricts = DistrictDialectData.allDistricts
        assertEquals("Should have exactly 64 districts", 64, allDistricts.size)
        assertEquals("Should have 8 divisions plus All filter", 9, DistrictDialectData.divisions.size)

        // Verify key districts have native phonetic texts
        val dhaka = allDistricts.find { it.districtEn == "Dhaka" }
        val chittagong = allDistricts.find { it.districtEn == "Chittagong" }
        val sylhet = allDistricts.find { it.districtEn == "Sylhet" }
        val noakhali = allDistricts.find { it.districtEn == "Noakhali" }

        assertTrue(dhaka != null && dhaka.sampleTextBn.isNotEmpty())
        assertTrue(chittagong != null && chittagong.sampleTextBn.isNotEmpty())
        assertTrue(sylhet != null && sylhet.sampleTextBn.isNotEmpty())
        assertTrue(noakhali != null && noakhali.sampleTextBn.isNotEmpty())
    }

    @Test
    fun testBanglishConverter() {
        val input = "ami banglay gaan gai"
        val output = BanglishConverter.convertBanglishToBangla(input)
        assertTrue("Output should contain Bengali script", output.any { it in '\u0980'..'\u09FF' })
    }

    @Test
    fun testTextChunkerUnderLimit() {
        val text = "সিয়াম ভয়েস এআই একটি উন্নত বাংলা ভয়েস ক্লোনিং প্ল্যাটফর্ম। এখানে ৬৪ জেলার আঞ্চলিক কণ্ঠ অন্তর্ভুক্ত।"
        val chunks = TextChunker.chunkText(text)
        assertFalse("Chunks should not be empty", chunks.isEmpty())
        assertTrue("Total estimated duration should be positive", TextChunker.getTotalEstimatedDuration(chunks) > 0f)
    }

    @Test
    fun testPretrainedStudioVoices() {
        val voices = PretrainedVoices.masterVoices
        assertTrue("Master voices should contain at least 8 profiles", voices.size >= 8)
        assertTrue(voices.any { it.gender.contains("পুরুষ") || it.gender.contains("Male") })
        assertTrue(voices.any { it.gender.contains("নারী") || it.gender.contains("Female") })
    }
}
