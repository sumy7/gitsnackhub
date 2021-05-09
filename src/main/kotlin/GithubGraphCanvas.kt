import kotlinx.browser.document
import org.w3c.dom.NodeList
import org.w3c.dom.get
import org.w3c.dom.svg.SVGRectElement

class GithubGraphCanvas : GraphCanvas {

    private val height: Int
    private val width: Int

    private val calendarDays: NodeList
    private val graphMap: Array<Array<CellType>>
    private var emptyCount = 0

    constructor() {
        this.calendarDays = document.querySelectorAll(".js-calendar-graph .js-calendar-graph-svg rect")
        this.height = 7
        this.width = this.calendarDays.length / 7
        this.graphMap = Array<Array<CellType>>(width) {
            Array<CellType>(height) {
                CellType.EMPTY
            }
        }
        this.emptyCount = height * width
        this.clearGraph()
    }

    private fun clearGraph() {
        var index = 0
        while (index < this.calendarDays.length) {
            val node = this.calendarDays[index] as SVGRectElement?
            if (node != null) {
                val dataLevel = document.createAttribute("data-level")
                dataLevel.nodeValue = CellType.EMPTY.value.toString()
                node.attributes.setNamedItem(dataLevel)
            }
            index += 1
        }
    }

    override fun getWidth(): Int {
        return this.width
    }

    override fun getHeight(): Int {
        return this.height
    }

    override fun randomEmpty(): Pair<Int, Int> {
        val randomIndex = (1..this.emptyCount).random()
        var count = 0
        for (i in 0 until width) {
            for (j in 0 until height) {
                if (graphMap[i][j] == CellType.EMPTY) {
                    count += 1
                    if (count >= randomIndex) {
                        return Pair(i, j)
                    }
                }
            }
        }
        return Pair(0, 0)
    }

    override fun render(snake: Snake, food: Pair<Int, Int>) {
        val tempGraphMap = Array<Array<CellType>>(width) {
            Array<CellType>(height) {
                CellType.EMPTY
            }
        }
        if ((food.first in 0 until width) && (food.second in 0 until height)) {
            tempGraphMap[food.first][food.second] = CellType.FOOD
        }

        var cellLevel = CellType.BODY_4
        for (pos in snake.iterator()) {
            if ((pos.first in 0 until width) && (pos.second in 0 until height)) {
                tempGraphMap[pos.first][pos.second] = cellLevel
                cellLevel = cellLevel.getNext()
            }
        }

        for (i in 0 until width) {
            for (j in 0 until height) {
                if (graphMap[i][j] != tempGraphMap[i][j]) {
                    val index = height * i + j
                    val node = this.calendarDays[index] as SVGRectElement?
                    if (node != null) {
                        val dataLevel = document.createAttribute("data-level")
                        dataLevel.nodeValue = tempGraphMap[i][j].value.toString()
                        node.attributes.setNamedItem(dataLevel)
                    }
                    graphMap[i][j] = tempGraphMap[i][j]
                    if (graphMap[i][j] == CellType.EMPTY) {
                        emptyCount += 1
                    } else {
                        emptyCount -= 1
                    }
                }
            }
        }

    }
}