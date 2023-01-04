package be.gkonen.calculator.domain

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import be.gkonen.calculator.R

object KeyboardHelper {

    operator fun get(id: Int) = idMap[id]

    fun getShape(id: Int) : RoundedCornerShape {
        val cornerRadius = 24.dp
        return when(id) {
            0 -> RoundedCornerShape(topStart = cornerRadius)
            3 -> RoundedCornerShape(topEnd = cornerRadius)
            16 -> RoundedCornerShape(bottomStart = cornerRadius)
            19 -> RoundedCornerShape(bottomEnd = cornerRadius)
            else -> RoundedCornerShape(0.dp)
        }
    }

    fun getWeight(id: Int) : Float = if (idMap[id] == ButtonConfig.B0) 2.0f else 1.0f

    private val idMap = mapOf(
       //First Line
       0 to ButtonConfig.AC,
       1 to ButtonConfig.PERCENT,
       2 to ButtonConfig.PLUS_MINUS,
       3 to ButtonConfig.DIVIDE,
       //Second Line
       4 to ButtonConfig.B7,
       5 to ButtonConfig.B8,
       6 to ButtonConfig.B9,
       7 to ButtonConfig.MULTIPLY,
       //Third Line
       8 to ButtonConfig.B4,
       9 to ButtonConfig.B5,
       10 to ButtonConfig.B6,
       11 to ButtonConfig.MINUS,
       //Fourth Line
       12 to ButtonConfig.B1,
       13 to ButtonConfig.B2,
       14 to ButtonConfig.B3,
       15 to ButtonConfig.ADD,
       // Fifth Line
       16 to ButtonConfig.B0,
       17 to null,
       18 to ButtonConfig.POINT,
       19 to ButtonConfig.EQUAL
   )

    enum class ButtonConfig(val type: ButtonType, val value: String? = null, val iconId: Int? = null) {
        AC(ButtonType.SPECIAL,"AC"), POINT(ButtonType.VALUE,"."),
        PLUS_MINUS(ButtonType.SPECIAL, iconId = R.drawable.ic_math_plusless),
        PERCENT(ButtonType.SPECIAL, iconId = R.drawable.ic_math_percent),
        ADD(ButtonType.OPERATOR, iconId = R.drawable.ic_math_plus ),
        MINUS(ButtonType.OPERATOR, iconId = R.drawable.ic_math_minus),
        DIVIDE(ButtonType.OPERATOR, iconId = R.drawable.ic_math_divide),
        MULTIPLY(ButtonType.OPERATOR, iconId = R.drawable.ic_math_multiplication),
        EQUAL(ButtonType.OPERATOR, iconId = R.drawable.ic_math_equal),
        B0(ButtonType.VALUE,"0"), B1(ButtonType.VALUE,"1"), B2(ButtonType.VALUE,"2"),
        B3(ButtonType.VALUE,"3"), B4(ButtonType.VALUE,"4"), B5(ButtonType.VALUE,"5"),
        B6(ButtonType.VALUE,"6"), B7(ButtonType.VALUE,"7"), B8(ButtonType.VALUE,"8"),
        B9(ButtonType.VALUE,"9")
    }

    enum class ButtonType {
        OPERATOR, VALUE, SPECIAL
    }

}