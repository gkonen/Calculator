package be.gkonen.calculator.domain

class MemberOperation(private var content: String = "") {

    private var isPositive = true

    fun clear() {
        isPositive = true
        content = ""
    }

    fun inverse() {
        isPositive = !isPositive
    }

    fun isEmpty() = content.isEmpty()

    fun isNotEmpty() = !isEmpty()

    private fun value(): Any? {
        return if(content.isEmpty()) null
        else if(content.contains(".")) {
            content.toFloat() * if (isPositive) 1 else -1
        } else {
            content.toInt() * if (isPositive) 1 else -1
        }
    }

    operator fun plusAssign(value: String) {
        content += if(value == "0") {
            if(content == "0") "" else value
        } else if(value == ".") {
            if(content.isBlank()) "0." else "."
        } else {
            value
        }
    }

    operator fun plus(other: MemberOperation): MemberOperation {
        val first = if(value() is Float) value() as Float? else value() as Int?
        val second = if(other.value() is Float) other.value() as Float? else other.value() as Int?

        if(first == null || second == null) return MemberOperation("")

        val result = if(first is Float || second is Float) {
            first.toFloat() + second.toFloat()
        } else {
            first.toInt() + second.toInt()
        }
        return MemberOperation( result.toString() )
    }

    operator fun minus(other: MemberOperation): MemberOperation {
        val first = if(value() is Float) value() as Float? else value() as Int?
        val second = if(other.value() is Float) other.value() as Float? else other.value() as Int?

        if(first == null || second == null) return MemberOperation("")

        val result = if(first is Float || second is Float) {
            first.toFloat() - second.toFloat()
        } else {
            first.toInt() - second.toInt()
        }
        return MemberOperation( result.toString())
    }

    operator fun times(other: MemberOperation): MemberOperation {
        val first = if(value() is Float) value() as Float? else value() as Int?
        val second = if(other.value() is Float) other.value() as Float? else other.value() as Int?

        if(first == null || second == null) return MemberOperation("")

        val result = if(first is Float || second is Float) {
            first.toFloat() * second.toFloat()
        } else {
            first.toInt() * second.toInt()
        }
        return MemberOperation( result.toString())
    }

    operator fun div(other: MemberOperation): MemberOperation {
        val first = if(value() is Float) value() as Float? else value() as Int?
        val second = if(other.value() is Float) other.value() as Float? else value() as Int?

        if(first == null || second == null) return MemberOperation("")
        if(second == 0) return MemberOperation("NaN")

        val result = if(first is Float || second is Float) {
            first.toFloat() / second.toFloat()
        } else {
            first.toFloat() / second.toInt()
        }
        return MemberOperation(result.toString())
    }

    override fun toString(): String {
        return if(!isPositive) "-$content" else content
    }
}
