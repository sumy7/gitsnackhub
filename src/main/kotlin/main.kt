import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.Event

fun main() {
    val jsCalendarGraphElement = document.querySelector(".js-calendar-graph")
    if (jsCalendarGraphElement != null) {
        val sg = document.querySelector(".js-calendar-graph > div .ContributionCalendar-day[data-level='4']")
        sg?.addEventListener("click", { e: Event ->
            val gameBoard = GameBoard()
            window.setInterval({
                gameBoard.handleKeyEvent()
            }, 20)
            window.setInterval({
                gameBoard.update()
                gameBoard.draw()
            }, 200)

            window.alert(
                """YOU GOT IT!
                arrow ↑↓←→ to move.
                space to reset game while game over.
                """.trimIndent()
            )

            gameBoard.start()

        }, js("{once: true}"))
    }
}
