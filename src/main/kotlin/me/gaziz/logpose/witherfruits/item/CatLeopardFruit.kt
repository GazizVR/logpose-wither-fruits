package me.gaziz.logpose.witherfruits.item

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text

class CatLeopardFruit: ZoanFruit("cat_leopard_fruit") {
    override var currentForm: Form = Form.Base
    override fun transform() {
        currentForm = Form.Full
    }
    override fun hybridTransform() {
        currentForm = Form.Hybrid
    }
    override fun baseTransform() {
        currentForm = Form.Base
    }

    fun toggleTransform(
        player: ServerPlayerEntity
    ) {
        currentForm = if(currentForm == Form.Full) Form.Base else Form.Full
        player.sendMessage(
            Text.literal(currentForm.name),
            true
        )
    }
}