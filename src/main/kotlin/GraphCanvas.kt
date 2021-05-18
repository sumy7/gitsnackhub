interface GraphCanvas {

    /**
     * 获取画布的宽度
     */
    fun getWidth(): Int

    /**
     * 获取画布的高度
     */
    fun getHeight(): Int

    /**
     * 随机获取一个空位置
     * @return 返回空位置的坐标
     */
    fun randomEmpty(): Pair<Int, Int>

    /**
     * 判断给定位置是否为空
     */
    fun checkEmpty(x: Int, y: Int): Boolean

    /**
     * 绘制画布
     */
    fun render(snake: Snake, food: Pair<Int, Int>)

}