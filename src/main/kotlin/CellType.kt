enum class CellType(val value: Int, val next: CellType) {
    EMPTY(0, EMPTY),
    BODY_1(1, BODY_1),
    BODY_2(2, BODY_1),
    BODY_3(3, BODY_2),
    BODY_4(4, BODY_3),
    FOOD(4, FOOD);
}