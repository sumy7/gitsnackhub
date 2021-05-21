interface GameBoard {

    /**
     * 启动Game
     */
    fun start()

    /**
     * 更新Game中的数据
     */
    fun update()

    /**
     * 绘制Game
     */
    fun draw()

    /**
     * 切换作弊状态
     * @return 当前是否处于作弊状态
     */
    fun switchCheatMode(): Boolean
}
