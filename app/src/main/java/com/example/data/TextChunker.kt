package com.example.data

data class TextChunk(
    val index: Int,
    val text: String,
    val isParagraphBreak: Boolean = false,
    val estimatedDurationSec: Float
)

object TextChunker {

    private const val MAX_CHUNK_LENGTH = 250

    fun chunkText(
        rawText: String,
        wordsPerMinute: Int = 135,
        sentencePauseSec: Float = 0.35f,
        paragraphPauseSec: Float = 0.75f
    ): List<TextChunk> {
        if (rawText.isBlank()) return emptyList()

        val chunks = mutableListOf<TextChunk>()
        val paragraphs = rawText.split(Regex("(\r\n|\n){2,}"))

        var chunkIndex = 0
        for ((pIndex, paragraph) in paragraphs.withIndex()) {
            val cleanParagraph = paragraph.trim()
            if (cleanParagraph.isEmpty()) continue

            val sentences = cleanParagraph.split(Regex("(?<=[।!?.\n])\\s+"))
            var currentBuffer = StringBuilder()

            for (sentence in sentences) {
                val s = sentence.trim()
                if (s.isEmpty()) continue

                if (currentBuffer.length + s.length > MAX_CHUNK_LENGTH && currentBuffer.isNotEmpty()) {
                    val chunkText = currentBuffer.toString().trim()
                    val wordCount = chunkText.split(Regex("\\s+")).size
                    val estDuration = (wordCount.toFloat() / wordsPerMinute * 60f) + sentencePauseSec
                    chunks.add(
                        TextChunk(
                            index = chunkIndex++,
                            text = chunkText,
                            isParagraphBreak = false,
                            estimatedDurationSec = estDuration
                        )
                    )
                    currentBuffer = StringBuilder()
                }

                if (currentBuffer.isNotEmpty()) currentBuffer.append(" ")
                currentBuffer.append(s)
            }

            if (currentBuffer.isNotEmpty()) {
                val chunkText = currentBuffer.toString().trim()
                val wordCount = chunkText.split(Regex("\\s+")).size
                val isLastParagraph = pIndex == paragraphs.size - 1
                val pause = if (isLastParagraph) sentencePauseSec else paragraphPauseSec
                val estDuration = (wordCount.toFloat() / wordsPerMinute * 60f) + pause
                chunks.add(
                    TextChunk(
                        index = chunkIndex++,
                        text = chunkText,
                        isParagraphBreak = !isLastParagraph,
                        estimatedDurationSec = estDuration
                    )
                )
            }
        }

        return chunks
    }

    fun getTotalEstimatedDuration(chunks: List<TextChunk>): Float {
        return chunks.sumOf { it.estimatedDurationSec.toDouble() }.toFloat()
    }
}
