interface GraphCanvas {

    fun getWidth(): Int

    fun getHeight(): Int

    fun randomEmpty(): Pair<Int, Int>

    fun render(snake: Snake, food: Pair<Int, Int>)

}