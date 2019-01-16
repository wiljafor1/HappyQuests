package me.devnatan.happymc.quests.plugin

import me.devnatan.happymc.quests.api.Quest
import me.devnatan.happymc.quests.api.QuestCompletable
import me.devnatan.happymc.quests.api.QuestListener
import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.objective.QuestObjective
import me.devnatan.happymc.quests.util.dsl.now
import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import kotlin.reflect.full.safeCast

/**
 * QuestCompletable
 */
fun QuestCompletable.isNotComplete() = !this.isComplete
fun QuestCompletable.ifNotComplete(block: QuestCompletable.() -> Unit) {
    if (isNotComplete()) block()
}
fun QuestCompletable.whenComplete(block: QuestCompletable.() -> Unit): QuestCompletable {
    if (!isNotComplete()) block()
    return this
}

/**
 * Quest
 */
fun Quest.questEvent(entity: LivingEntity): QuestEvent = QuestEvent(entity, this, this.currentObjective!!, now())
operator fun Quest.iterator(): MutableIterator<QuestObjective> = objectives.iterator()
operator fun Quest.plusAssign(objective: QuestObjective) { objectives.add(objective) }

/**
 * Quest Objective
 */
fun QuestObjective.ifActive(block: QuestObjective.() -> Unit) {
    if (active) block()
}

/**
 * QuestListener
 */
inline fun <reified T : Event> QuestListener.event(
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = true,
        crossinline block: T.() -> Unit
) {
    register { plugin ->
        plugin.logger.info("Event [${T::class.simpleName}]${if(delegate != null)
            " with delegate" else ""
        } registered.")
        Bukkit.getServer().pluginManager.registerEvent(
            T::class.java,
            delegate ?: this,
            priority,
            { _, e -> T::class.safeCast(e)?.block() },
            plugin,
            ignoreCancelled)
    }
}
fun QuestListener.unregister() = HandlerList.unregisterAll(delegate ?: this)
