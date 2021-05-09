enum class CellType(val value: Int) {
    EMPTY(0),
    BODY_1(1),
    BODY_2(2),
    BODY_3(3),
    BODY_4(4),
    FOOD(4);

    fun getNext(): CellType {
        when {
            this == EMPTY -> {
                return EMPTY
            }
            this == BODY_1 -> {
                return BODY_1
            }
            this == BODY_2 -> {
                return BODY_1
            }
            this == BODY_3 -> {
                return BODY_2
            }
            this == BODY_4 -> {
                return BODY_3
            }
            this == FOOD -> {
                return FOOD
            }
            else -> {
                return EMPTY
            }
        }
    }
}
