package be.gkonen.calculator.ui.theme


//Based on "https://gist.github.com/sczerwinski/f47fa93e37f4f9263562e86b60f1681f"
enum class MaterialColor(val tones: Map<Int,Int> = emptyMap(), val singleColor: Int? ) {
    Primary(
        tones = mapOf(
            10 to 0x001947,
            20 to 0x002c71,
            30 to 0x00419f,
            40 to 0x0056d0,
            50 to 0x276ff4,
            60 to 0x5a8cff,
            70 to 0x87a9ff,
            80 to 0xb1c5ff,
            90 to 0xdae2ff
        ),
        singleColor = 0x0060E5
    ),
    Secondary(
        tones = mapOf(
            10 to 0x002113,
            20 to 0x003824,
            30 to 0x005236,
            40 to 0x006c49,
            50 to 0x00885d,
            60 to 0x26a474,
            70 to 0x49bf8d,
            80 to 0x68dca7,
            90 to 0x85f8c2
        ),
        singleColor = 0x26a474
    ),
    Tertiary(
        tones = mapOf(
            10 to 0x291800,
            20 to 0x452b00,
            30 to 0x533500,
            40 to 0x825500,
            50 to 0xa26c00,
            60 to 0xc48300,
            70 to 0xe89c00,
            80 to 0xffb94e,
            90 to 0xffddb2
        ),
        singleColor = 0xe89c00
    ),
    Error(
        tones = mapOf(
            10 to 0x410002,
            20 to 0x690005,
            30 to 0x93000a,
            40 to 0xba1a1a,
            50 to 0xde3730,
            60 to 0xff5449,
            70 to 0xff897d,
            80 to 0xffb4ab,
            90 to 0xffdad6
        ),
        singleColor = null
    ),
    Neutral(
        tones = mapOf(
            10 to 0x001c38,
            20 to 0x00315b,
            30 to 0x004880,
            40 to 0x1260a4,
            50 to 0x387abf,
            60 to 0x5694db,
            70 to 0x72aef8,
            80 to 0xa1c9ff,
            90 to 0xd3e4ff
        ),
        singleColor = 0x161A20
    ),
    NeutralVariant(
        tones = mapOf(
            10 to 0x191b23,
            20 to 0x2e3038,
            30 to 0x44464f,
            40 to 0x5c5e67,
            50 to 0x757780,
            60 to 0x8f9099,
            70 to 0xaaabb4,
            80 to 0xc5c6d0,
            90 to 0xe1e2ec
        ),
        singleColor = null
    );

    private val COLOR_BLACK = 0x000000
    private val COLOR_WHITE = 0xFFFFFF

    private fun getTone(tone: Int): Int? =
        if (tones.isEmpty()) null
        else tones[tone] ?: interpolate(tones, tone)

    operator fun get(tone: Int): Int = getTone(tone) ?: COLOR_BLACK
    operator fun invoke(): Int = singleColor ?: getTone(50) ?: COLOR_BLACK

    private fun interpolate(tones: Map<Int, Int>, tone: Int): Int {
        val lowTone = tones.keys.filter { tone >= it }.maxOrNull() ?: 0
        val highTone = tones.keys.filter { tone <= it }.minOrNull() ?: 100
        val lowColor = tones[lowTone] ?: COLOR_WHITE
        val highColor = tones[highTone] ?: COLOR_BLACK
        return colorInt(
            interpolateChannel(lowTone, highTone, tone, lowColor.red(), highColor.red()),
            interpolateChannel(lowTone, highTone, tone, lowColor.green(), highColor.green()),
            interpolateChannel(lowTone, highTone, tone, lowColor.blue(), highColor.blue()))
    }

    private fun interpolateChannel(lowTone: Int, highTone: Int, tone: Int, lowValue: Int, highValue: Int): Int =
        (lowValue * (highTone - tone) + highValue * (tone - lowTone)) / (highTone - lowTone)


    private fun Int.red() = (this and 0xFF0000) ushr 16
    private fun Int.green() = (this and 0x00FF00) ushr 8
    private fun Int.blue() = this and 0x0000FF

    private fun colorInt(red: Int, green: Int, blue: Int) = (red shl 16) + (green shl 8) + blue

}