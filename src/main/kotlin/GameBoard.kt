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
        val tempSnake = Snake(tempGraphCanvas.getWidth() / 2, tempGraphCanvas.getHeight() / 2)

        graphCanvas = tempGraphCanvas
        snake = tempSnake
        keyBoard = KeyBoard()
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
        val tempSnake = Snake(tempGraphCanvas.getWidth() / 2, tempGraphCanvas.getHeight() / 2)

        graphCanvas = tempGraphCanvas
        snake = tempSnake
        food = graphCanvas!!.randomEmpty()

        isGameOver = false
        isStart = false
        inited = true
    }

    fun update() {
        if (this.inited) {
            if (this.isGameOver) {
                if (keyBoard!!.isPressed(KeyBoard.SPACE)) {
                    restart()
                }
                return
            }

            // 判断按键
            var nextDirection = this.direction
            if (keyBoard!!.isPressed(KeyBoard.UP)) {
                if (this.direction !== DIRECTION.BOTTOM || !this.isStart) {
                    nextDirection = DIRECTION.TOP
                }
                this.isStart = true
            }
            if (keyBoard!!.isPressed(KeyBoard.DOWN)) {
                if (this.direction !== DIRECTION.TOP || !this.isStart) {
                    nextDirection = DIRECTION.BOTTOM
                }
                this.isStart = true
            }
            if (keyBoard!!.isPressed(KeyBoard.LEFT)) {
                if (this.direction !== DIRECTION.RIGHT || !this.isStart) {
                    nextDirection = DIRECTION.LEFT
                }
                this.isStart = true
            }
            if (keyBoard!!.isPressed(KeyBoard.RIGHT)) {
                if (this.direction !== DIRECTION.LEFT || !this.isStart) {
                    nextDirection = DIRECTION.RIGHT
                }
                this.isStart = true
            }
            this.direction = nextDirection

            // 未开始游戏时，只响应按键，不处理移动
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