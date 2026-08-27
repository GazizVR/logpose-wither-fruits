package me.gaziz.logpose.witherfruits.fruit.zoan

import me.gaziz.logpose.witherfruits.PlayerManager
import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import me.gaziz.logpose.witherfruits.modifier.BuffManager
import me.gaziz.logpose.witherfruits.modifier.Modifier
import net.minecraft.entity.EntityType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Rarity

abstract class ZoanFruit(
    path: String,
): WitherFruit(
    path = path,
    rarity = Rarity.RARE,
    tooltipKey = ModFruits.zoanTooltipKey
) {
    enum class Form { Base, Full, Hybrid }

    protected var currentForm = mutableMapOf<String,Form>()
    protected fun getUserForm(
        key: String
    ): Form? {
        return currentForm[key]
    }
    protected fun setUserForm(
        key: String,
        value: Form
    ) {
        currentForm[key] = value
    }
    private fun removeModifiers(player: ServerPlayerEntity){
        BuffManager.removeModifiers(player)
        BuffManager.removeEffects(player)
        if(getUserForm(player.uuidAsString) == Form.Full) {
            PlayerManager.removeEntityType(player)
        }
    }
    private fun toggleForm(
        form: Form,
        player: ServerPlayerEntity,
        effects: List<StatusEffectInstance>,
        modifiers: List<Modifier>,
        hungerCost: Int
    ) {
        val key = player.uuidAsString
        if(getUserForm(key) == form){
            removeModifiers(player)
            setUserForm(key,Form.Base)
        } else {
            if(player.hungerManager.foodLevel >= hungerCost) {
                removeModifiers(player)
                if(form == Form.Full) {
                    PlayerManager.setEntityTypeAndSend(player, transformEntityType)
                }
                BuffManager.setEffects(player.uuidAsString,effects)
                BuffManager.setModifiers(player.uuidAsString,modifiers)
                player.hungerManager.foodLevel -= hungerCost
                setUserForm(key,form)
            } else {
                player.sendMessage(Text.literal("Not enough satiety"),true)
            }
        }
    }

    abstract val transformEffects: List<StatusEffectInstance>
    abstract val transformModifiers: List<Modifier>
    abstract val transformEntityType: EntityType<*>
    abstract val transformHungerCost: Int
    fun toggleTransform(player: ServerPlayerEntity) {
        toggleForm(
            form = Form.Full,
            player = player,
            effects = transformEffects,
            modifiers = transformModifiers,
            hungerCost = transformHungerCost
        )
    }

    abstract val hybridEffects: List<StatusEffectInstance>
    abstract val hybridModifiers: List<Modifier>
    abstract val hybridHungerCost: Int
    fun toggleHybridForm(player: ServerPlayerEntity) {
        toggleForm(
            form = Form.Hybrid,
            player = player,
            effects = hybridEffects,
            modifiers = hybridModifiers,
            hungerCost = hybridHungerCost
        )
    }
}