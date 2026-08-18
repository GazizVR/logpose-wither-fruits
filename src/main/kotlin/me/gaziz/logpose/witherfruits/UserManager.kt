package me.gaziz.logpose.witherfruits

object UserManager {
    var fruit: WitherFruit? = null
    private set
    fun setFruit(f: WitherFruit?) {
        fruit = f
    }
}