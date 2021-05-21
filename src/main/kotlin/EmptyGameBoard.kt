class EmptyGameBoard : GameBoard {
    override fun start() {
        // do nothing
    }

    override fun update() {
        // do nothing
    }

    override fun draw() {
        // do nothing
    }

    override fun switchCheatMode(): Boolean {
        return false
    }
}