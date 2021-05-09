import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

class KeyBoard {

    companion object {
        val LEFT = 37
        val UP = 38
        val RIGHT = 39
        val DOWN = 40
        val SPACE = 32
    }

    private val pressed = HashMap<Int, Boolean>()

    constructor() {
        window.addEventListener("keyup", { event: Event ->
            val keyCode = (event as KeyboardEvent).keyCode
            onKeyUp(keyCode)
            if (keyCode in arrayOf(LEFT, UP, RIGHT, DOWN, SPACE)) {
                event.preventDefault()
            }
        })
        window.addEventListener("keydown", { event: Event ->
            val keyCode = (event as KeyboardEvent).keyCode
            onKeyDown(keyCode)
            if (keyCode in arrayOf(LEFT, UP, RIGHT, DOWN, SPACE)) {
                event.preventDefault()
            }
        })
    }

    fun isPressed(key: Int): Boolean {
        return pressed[key] ?: false
    }

    private fun onKeyDown(key: Int) {
        pressed[key] = true
    }

    private fun onKeyUp(key: Int) {
        pressed[key] = false
    }
}