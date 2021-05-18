class SnakeBody(
    val x: Int,
    val y: Int,
    var next: SnakeBody? = null,
    var prev: SnakeBody? = null
) {

}

enum class DIRECTION(val x: Int, val y: Int) {
    TOP(0, -1),
    RIGHT(1, 0),
    BOTTOM(0, 1),
    LEFT(-1, 0);
}


class Snake(
    headX: Int,                   // 初始位置X坐标
    headY: Int,                   // 初始位置Y坐标
    private val boundWidth: Int,  // 活动范围宽度
    private val boundHeight: Int  // 活动范围高度
) : Iterable<Pair<Int, Int>> {

    private var head = SnakeBody(headX, headY)
    private var tail = head

    private var size = 1
    private var dire: DIRECTION = DIRECTION.LEFT

    fun stepTo(direction: DIRECTION, eat: Boolean) {
        val nextX = (head.x + direction.x + boundWidth) % boundWidth
        val nextY = (head.y + direction.y + boundHeight) % boundHeight

        val snakeBody = SnakeBody(nextX, nextY)
        head.prev = snakeBody
        snakeBody.next = head
        head = snakeBody

        this.dire = direction

        if (!eat) {
            tail.prev!!.next = null
            tail = tail.prev!!
        } else {
            size += 1
        }
    }

    fun getHead(): Pair<Int, Int> {
        return Pair(head.x, head.y)
    }

    fun getDirection(): DIRECTION {
        return this.dire
    }

    fun getNext(direction: DIRECTION): Pair<Int, Int> {
        val nextX = (head.x + direction.x + boundWidth) % boundWidth
        val nextY = (head.y + direction.y + boundHeight) % boundHeight
        return Pair(nextX, nextY)
    }

    fun checkPoint(x: Int, y: Int): Boolean {
        var cur: SnakeBody? = head
        while (cur != null) {
            if (cur.x == x && cur.y == y) {
                return true
            }
            cur = cur.next
        }
        return false
    }

    override fun iterator(): Iterator<Pair<Int, Int>> {
        return object : Iterator<Pair<Int, Int>> {
            private var node: SnakeBody? = this@Snake.head

            override fun hasNext(): Boolean {
                return node != null
            }

            override fun next(): Pair<Int, Int> {
                return if (node == null) {
                    Pair(-1, -1)
                } else {
                    val pos = Pair(node!!.x, node!!.y)
                    node = node!!.next
                    pos
                }
            }

        }
    }

}