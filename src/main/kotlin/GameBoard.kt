class GameBoard {

    private var isGameOver = true
    private var isStart = false

    private var graphCanvas: GraphCanvas? = null
    private var snake: Snake? = null
    private var food: Pair<Int, Int>? = null
    private var direction: DIRECTION = DIRECTION.BOTTOM

    fun start() {
        if (!isGameOver) {
            return
        }
        val tempGraphCanvas = GithubGraphCanvas()
        val tempSnake = Snake(tempGraphCanvas.getWidth() / 2, tempGraphCanvas.getHeight() / 2)

        graphCanvas = tempGraphCanvas
        snake = tempSnake
        isGameOver = false
        isStart = false
    }

    fun update() {
        if (this.isStart) {
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