package me.gaziz.logpose.witherfruits

object UsersManager {
    var fruits: Map<String,WitherFruit> = emptyMap()
    private set
    fun setFruit(
        uuid: String,
        f: WitherFruit
    ) {
        fruits = fruits + (uuid to f)
    }
    fun removeFruit(uuid: String) {
        fruits = fruits - uuid
    }
}