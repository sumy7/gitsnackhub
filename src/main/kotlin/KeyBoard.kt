import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

typealias keyEventHandler = (keyCode: Int) -> Unit

enum class KeyBoardKey(val keyCode: Int) {
    LEFT(37),
    UP(38),
    RIGHT(39),
    DOWN(40),
    SPACE(32);

    companion object {
        /**
         * 所有支持回调函数的KeyCode
         */
        val supportKeyCode: List<Int> = ArrayList<Int>().apply {
            this.addAll(values().map { it.keyCode })
        }
    }
}

/**
 * 处理键盘事件
 */
class KeyBoard {

    private val pressed = HashMap<Int, Boolean>()
    private val eventsMap = HashMap<Int, ArrayList<keyEventHandler>>()

    constructor() {
        window.addEventListener("keyup", { event: Event ->
            val keyCode = (event as KeyboardEvent).keyCode
            onKeyUp(keyCode)
            if (keyCode in KeyBoardKey.supportKeyCode) {
                event.preventDefault()
            }
        })
        window.addEventListener("keydown", { event: Event ->
            val keyCode = (event as KeyboardEvent).keyCode
            onKeyDown(keyCode)
            if (keyCode in KeyBoardKey.supportKeyCode) {
                event.preventDefault()
                try {
                    if (eventsMap.containsKey(keyCode)) {
                        for (handler in eventsMap[keyCode]!!) {
                            handler(keyCode)
                        }
                    }
                } catch (e: Exception) {
                    console.error(e)
                }
            }
        })
    }

    /**
     * 判断某个键盘按键是否按下
     */
    fun isPressed(key: Int): Boolean {
        return pressed[key] ?: false
    }

    private fun onKeyDown(key: Int) {
        pressed[key] = true
    }

    private fun onKeyUp(key: Int) {
        pressed[key] = false
    }

    /**
     * 注册键盘事件回调
     */
    fun on(key: KeyBoardKey, handler: keyEventHandler) {
        var handlerList = eventsMap[key.keyCode]
        if (handlerList != null) {
            handlerList.add(handler)
        } else {
            handlerList = ArrayList()
            handlerList.add(handler)
            eventsMap[key.keyCode] = handlerList
        }
    }
}