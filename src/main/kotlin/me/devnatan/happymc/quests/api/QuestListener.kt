package me.devnatan.happymc.quests.api

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.reflect.full.safeCast

typealias QuestListenerEventBlock = (Plugin) -> Unit

interface QuestListener : Listener {

    var plugin: Plugin?

    var isUseListeners: Boolean

    var delegate: QuestListener?

    var events: MutableList<QuestListenerEventBlock>

    var playerData: ConcurrentMap<UUID, Pair<String, Any?>>

    fun register(block: QuestListenerEventBlock) {
        events.add(block)
    }

    fun call() {
        val iterator = events.iterator()
        while (iterator.hasNext()) {
            iterator.next()(plugin!!)
            iterator.remove()
        }
    }

}

inline fun <reified T : Event> QuestListener.event(
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = true,
        crossinline block: T.() -> Unit) {
    register {
        it.logger.info("Event [${T::class.simpleName}] registered" +
                if (delegate != null)
                    " with delegate"
                else "")
        Bukkit.getServer().pluginManager.registerEvent(
                T::class.java,
                delegate ?: this,
                priority,
                { _, e ->
                    T::class.safeCast(e)?.block()
                },
                it,
                ignoreCancelled)
    }
}

fun listener(plugin: Plugin, block: QuestListener.() -> Unit) {
    object : QuestListener {
        override var plugin: Plugin? = plugin
        override var delegate: QuestListener? = null
        override var events: MutableList<QuestListenerEventBlock> = mutableListOf()
        override var isUseListeners: Boolean = true
        override var playerData: ConcurrentMap<UUID, Pair<String, Any?>> = ConcurrentHashMap()

        init {
            block(this)
            call()
        }
    }
}

fun QuestListener.unregister() {
    events.clear()
    playerData.clear()
    HandlerList.unregisterAll(this)
}
/* fun QuestListener.lockMovement(event: QuestEvent, duration: Long = 1000L, block: () -> Unit = {}) {
    if (!playerData.containsKey(event.actor.uniqueId))
        playerData[event.actor.uniqueId] = "lockMovement" to now()

    scheduler {
        if (!playerData.containsKey(event.actor.uniqueId))
            cancelAll()
        else {
            val pair = playerData[event.actor.uniqueId] as Pair<String, Any?>
            if (pair.first == "lockMovement") {
                task(millisToTicks(duration)) {
                    val elapsed = (now() - (pair.second as Long))
                    if (elapsed >= duration) {
                        playerData.remove(event.actor.uniqueId, pair)
                        cancelAll()
                    } else block()
                }
            }
        }
    }
} */