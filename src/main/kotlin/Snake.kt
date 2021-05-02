class SnakeBody(
    val x: Int,
    val y: Int,
    var next: SnakeBody? = null,
    var prev: SnakeBody? = null
) {

}

enum class DIRECTION(val x: Int, val y: Int) {
    TOP(-1, 0),
    RIGHT(0, 1),
    BOTTOM(1, 0),
    LEFT(0, -1);
}


class Snake(headX: Int, headY: Int) : Iterable<Pair<Int, Int>> {

    private var head = SnakeBody(headX, headY)
    private var tail = head

    private var size = 1

    fun stepTo(direction: DIRECTION, eat: Boolean) {
        val nextX = head.x + direction.x
        val nextY = head.y + direction.y

        val snakeBody = SnakeBody(nextX, nextY)
        head.prev = snakeBody
        snakeBody.next = head
        head = snakeBody

        if (!eat) {
            tail.prev!!.next = null
            tail = tail.prev!!
        } else {
            size += 1
        }
    }

    fun getNext(direction: DIRECTION): Pair<Int, Int> {
        val nextX = head.x + direction.x
        val nextY = head.y + direction.y
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