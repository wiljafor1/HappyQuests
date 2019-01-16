package me.devnatan.happymc.quests.util

@Deprecated("Deprecated")
enum class Positions(
        var number: Int,
        var readable: String
) {

    ONE(1, "Primeiro"),
    TWO(2, "Segundo"),
    THREE(3, "Terceiro"),
    FOUR(4, "Quarto"),
    FIVE(5, "Quinto"),
    SIX(6, "Sexto"),
    SEVEN(7, "Sétimo"),
    EIGTH(8, "Oitavo"),
    NINE(9, "Nono"),
    TEN(10, "Décimo"),
    TWENTY(20, "Vigésimo");

    companion object {
        fun valueOf(number: Int): String? {
            for (value in values()) {
                if (value.number == number) return value.readable
            }
            return null
        }
    }

}

@Deprecated("Deprecated")
object PositionUtil {

    internal val CACHED: MutableMap<Int, Positions> = mutableMapOf()

}

@Deprecated("Deprecated")
fun Int.toPosition(): String {
    if (PositionUtil.CACHED.containsKey(this)) {
        return PositionUtil.CACHED[this]!!.readable
    }

    val pos = Positions.valueOf(this)
    return if (pos != null) pos
    else {
        var res = ""
        var that = this
        for (position in Positions.values().reversed()) {
            if (that <= 0) break
            if (position.number <= that) {
                res += position.readable + " "
                that -= position.number
                println(that)
            }
        }
        return res.removeSuffix(" ")
    }
}