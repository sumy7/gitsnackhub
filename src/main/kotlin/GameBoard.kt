class GameBoard {

    private var inited = false     // 游戏是否初始化完成
    private var isGameOver = true  // 本轮游戏是否已经结束
    private var isStart = false    // 游戏是否正在运行

    private var graphCanvas: GraphCanvas? = null
    private var keyBoard: KeyBoard? = null
    private var snake: Snake? = null
    private var food: Pair<Int, Int>? = null
    private var direction: DIRECTION = DIRECTION.BOTTOM

    fun start() {
        if (!isGameOver || inited) {
            return
        }
        val tempGraphCanvas = GithubGraphCanvas()
        val tempSnake = Snake(
            tempGraphCanvas.getWidth() / 2,
            tempGraphCanvas.getHeight() / 2,
            tempGraphCanvas.getWidth(),
            tempGraphCanvas.getHeight()
        )

        graphCanvas = tempGraphCanvas
        snake = tempSnake
        keyBoard = KeyBoard()
        initKeyEvent(keyBoard!!)
        food = graphCanvas!!.randomEmpty()

        isGameOver = false
        isStart = false
        inited = true
    }

    fun restart() {
        if (!isGameOver || !inited) {
            return
        }

        val tempGraphCanvas = GithubGraphCanvas()
        val tempSnake = Snake(
            tempGraphCanvas.getWidth() / 2,
            tempGraphCanvas.getHeight() / 2,
            tempGraphCanvas.getWidth(),
            tempGraphCanvas.getHeight()
        )

        graphCanvas = tempGraphCanvas
        snake = tempSnake
        food = graphCanvas!!.randomEmpty()

        isGameOver = false
        isStart = false
        inited = true
    }

    /**
     * 初始化键盘事件
     */
    private fun initKeyEvent(keyBoard: KeyBoard) {
        keyBoard.on(KeyBoardKey.SPACE) {
            if (inited) {
                if (isGameOver) { // 游戏失败时重启游戏
                    restart()
                } else { // 否则切换暂停状态
                    isStart = !isStart
                }
            }
        }
        keyBoard.on(KeyBoardKey.UP) {
            if (inited && !isGameOver) {
                if (snake!!.getDirection() !== DIRECTION.BOTTOM || !isStart) {
                    direction = DIRECTION.TOP
                }
                isStart = true
            }
        }
        keyBoard.on(KeyBoardKey.DOWN) {
            if (inited && !isGameOver) {
                if (snake!!.getDirection() !== DIRECTION.TOP || !isStart) {
                    direction = DIRECTION.BOTTOM
                }
                isStart = true
            }
        }
        keyBoard.on(KeyBoardKey.LEFT) {
            if (inited && !isGameOver) {
                if (snake!!.getDirection() !== DIRECTION.RIGHT || !isStart) {
                    direction = DIRECTION.LEFT
                }
                isStart = true
            }
        }
        keyBoard.on(KeyBoardKey.RIGHT) {
            if (inited && !isGameOver) {
                if (snake!!.getDirection() !== DIRECTION.LEFT || !isStart) {
                    direction = DIRECTION.RIGHT
                }
                isStart = true
            }
        }
    }

    fun update() {
        if (this.inited) {

            // 未开始游戏时，不处理移动
            if (!this.isStart) {
                return
            }

            val nextPos = snake!!.getNext(this.direction)
            if ((nextPos.first !in 0 until this.graphCanvas!!.getWidth()) ||
                (nextPos.second !in 0 until this.graphCanvas!!.getHeight()) ||
                snake!!.checkPoint(nextPos.first, nextPos.second)
            ) {
                this.isGameOver = true
                this.isStart = false
                return
            }
            val eat = (nextPos.first == food!!.first) && (nextPos.second == food!!.second)
            if (eat) {
                food = this.graphCanvas!!.randomEmpty()
            }
            this.snake!!.stepTo(this.direction, eat)
        }
    }

    fun draw() {
        if (!this.isGameOver) {
            this.graphCanvas!!.render(this.snake!!, this.food!!)
        }
    }

}