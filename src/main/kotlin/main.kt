import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.Event

fun main() {
    val jsCalendarGraphElement = document.querySelector(".js-calendar-graph")
    if (jsCalendarGraphElement != null) {
        var gameBoard: GameBoard = EmptyGameBoard()
        val sg = document.querySelector(".js-calendar-graph > div .ContributionCalendar-day[data-level='4']")
        sg?.addEventListener("dblclick", { _: Event ->
            gameBoard = DefaultGameBoard()
            window.setInterval({
                gameBoard.update()
                gameBoard.draw()
            }, 200)

            window.alert(
                """
                YOU GOT IT!
                arrow ↑↓←→ to move.
                space to reset game while game over.
                """.trimIndent()
            )

            gameBoard.start()

        }, js("{once: true}"))

        val cheatFlag = document.querySelector(".js-calendar-graph > div .ContributionCalendar-day[data-level='0']")
        cheatFlag?.addEventListener("dblclick", {
            val isCheatMode = gameBoard.switchCheatMode()
            window.alert("CHEAT MODE: " + if (isCheatMode) "ON" else "OFF")
        })

    }
}
