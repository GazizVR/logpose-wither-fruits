package me.gaziz.logpose.witherfruits

import com.mojang.serialization.Codec
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.world.PersistentState
import kotlin.jvm.optionals.getOrElse

class PersistFruitsState(
    fs: Map<String, WitherFruit> = emptyMap()
): PersistentState() {
    private var fruits: Map<String, WitherFruit> = emptyMap()
    init {
        fruits = fs
    }
    fun getFruits(): Map<String, WitherFruit> {
        return fruits
    }
    fun setFruit(
        uuid: String,
        f: WitherFruit
    ) {
        fruits = fruits + (uuid to f)
        markDirty()
    }
    fun removeFruit(uuid: String) {
        fruits = fruits - uuid
        markDirty()
    }

    private val codec = Codec.unboundedMap(
        Codec.STRING,
        Codec.STRING.xmap<WitherFruit>(
            { idStr ->
                val id = Identifier.tryParse(idStr) ?: return@xmap null
                val fruit = Registries.ITEM.get(id)
                fruit as? WitherFruit
            },
            { fruit ->
                fruit.id.toString()
            }
        )
    )
    fun getPersistFruitsState(
        server: MinecraftServer,
    ): PersistFruitsState {
        val world = server.getWorld(ServerWorld.OVERWORLD) ?: return PersistFruitsState(emptyMap())
        return world.persistentStateManager.getOrCreate(
            type,
            NBT_ID
        )
    }
    companion object {
        private const val NBT_ID = "${Initializer.MOD_ID}_fruits"
    }
    private fun readNBT(
        nbTag: NbtCompound,
        reg: RegistryWrapper.WrapperLookup
    ): PersistFruitsState {
        val loadFruits = codec
            .parse(
                NbtOps.INSTANCE,
                nbTag.getCompound(NBT_ID),
            )
            .result()
            .getOrElse { emptyMap() }
        return PersistFruitsState(loadFruits)
    }
    private val type = Type(
        ::PersistFruitsState,
        { tag, reg -> readNBT(tag,reg) },
        null,
    )
    override fun writeNbt(
        nbTag: NbtCompound,
        registryLookup: RegistryWrapper.WrapperLookup
    ): NbtCompound {
        codec
            .encodeStart(
                NbtOps.INSTANCE,
                this.fruits
            )
            .result()
            .ifPresent { nbTag.put(NBT_ID, it) }
        return nbTag
    }
}