package me.gaziz.logpose.witherfruits.item

import me.gaziz.logpose.witherfruits.PlayerManager
import me.gaziz.logpose.witherfruits.modifier.BuffManager
import me.gaziz.logpose.witherfruits.modifier.Modifier
import me.gaziz.logpose.witherfruits.modifier.createEffect
import me.gaziz.logpose.witherfruits.modifier.createModifier
import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text

class CatLeopardFruit: ZoanFruit("cat_leopard_fruit") {
    override var currentForm: Form = Form.Base

    private val transformEffects = listOf(
        createEffect(StatusEffects.NIGHT_VISION),
        createEffect(StatusEffects.HUNGER),
    )
    private fun createModifier(
        attr: RegistryEntry<EntityAttribute>,
        value: Double,
    ): Modifier {
        val prefix = "leopard"
        return createModifier(attr, value, prefix)
    }
    private val transformModifiers = listOf(
        createModifier(EntityAttributes.GENERIC_SCALE,-0.5),
        createModifier(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED,-1.0),
        createModifier(EntityAttributes.GENERIC_ARMOR,4.0),
        //Damage
        createModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,3.0),
        createModifier(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,-0.5),
        //Movement
        createModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.05),
        createModifier(EntityAttributes.PLAYER_SNEAKING_SPEED,0.3),
        createModifier(EntityAttributes.GENERIC_STEP_HEIGHT,0.4),
        //Jump
        createModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,0.2),
        createModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,3.0),
        createModifier(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER,-0.25),
    )

    private fun removeModifiers(
        player: ServerPlayerEntity
    ) {
        BuffManager.removeModifiers(player)
        BuffManager.removeEffects(player)
        if(currentForm == Form.Full) {
            PlayerManager.removeEntityType(player)
        }
    }

    private val transformEntityType = EntityType.OCELOT
    override fun toggleTransform(
        player: ServerPlayerEntity
    ) {
        if(currentForm == Form.Full){
            removeModifiers(player)
            currentForm = Form.Base
        } else {
            val hungerCost = 2
            if(player.hungerManager.foodLevel >= hungerCost) {
                removeModifiers(player)
                PlayerManager.setEntityType(player, EntityType.OCELOT)
                player.server.playerManager.playerList.forEach {
                    NetworkManager.sendEntityTypeS2C(
                        it,
                        player.uuidAsString,
                        transformEntityType
                    )
                }
                BuffManager.setEffects(player.uuidAsString,transformEffects)
                BuffManager.setModifiers(player.uuidAsString,transformModifiers)
                player.hungerManager.foodLevel -= hungerCost
                currentForm = Form.Full
            } else {
                player.sendMessage(Text.literal("Not enough satiety"),true)
            }
        }
    }

    private val hybridEffects = listOf(
        createEffect(StatusEffects.NIGHT_VISION),
        createEffect(StatusEffects.HUNGER,1)
    )
    private val hybridModifiers = listOf(
        createModifier(EntityAttributes.GENERIC_SCALE,0.67),
        createModifier(EntityAttributes.GENERIC_STEP_HEIGHT,0.4),
        createModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,0.5),
        createModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,0.5),
        //Damage
        createModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,6.0),
        createModifier(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,0.3),
        //Defense
        createModifier(EntityAttributes.GENERIC_MAX_HEALTH,6.0),
        createModifier(EntityAttributes.GENERIC_ARMOR,8.0),
        createModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,4.0),
        createModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,0.3),
        createModifier(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE,0.5),
        //Movement
        createModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.025),
        createModifier(EntityAttributes.PLAYER_SNEAKING_SPEED,0.15),
        //Jump
        createModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,0.13),
        createModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,1.25),
        createModifier(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER,-0.125),
    )

    override fun toggleHybridForm(
        player: ServerPlayerEntity
    ) {
        if(currentForm == Form.Hybrid) {
            removeModifiers(player)
            currentForm = Form.Base
        } else {
            val hungerCost = 4
            if(player.hungerManager.foodLevel >= hungerCost) {
                removeModifiers(player)
                BuffManager.setEffects(player.uuidAsString,hybridEffects)
                BuffManager.setModifiers(player.uuidAsString,hybridModifiers)
                player.hungerManager.foodLevel -= hungerCost
                currentForm = Form.Hybrid
            } else {
                player.sendMessage(Text.literal("Not enough satiety"),true)
            }
        }
    }
}