package me.devnatan.happymc.quests.plugin

import me.devnatan.happymc.quests.api.objetive.QuestObjective
import me.devnatan.happymc.quests.api.quest.Quest
import me.devnatan.happymc.quests.api.quest.QuestCompletable
import me.devnatan.happymc.quests.api.quest.QuestListener
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import kotlin.reflect.full.safeCast

/**
 * QuestCompletable
 */
fun QuestCompletable.isNotComplete() = !this.isComplete
fun QuestCompletable.canComplete() = !this.isComplete

/**
 * Quest
 */
operator fun Quest.iterator(): MutableIterator<QuestObjective> = objectives.iterator()
operator fun Quest.plusAssign(objective: QuestObjective) { withObjective(objective) }

/**
 * QuestObjective
 */
fun QuestObjective.next(block: QuestObjective?.() -> Unit) {
    if (next != null) block(next)
}

/**
 * QuestListener
 */
inline fun <reified T : Event> QuestListener.event(
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = true,
        crossinline block: T.() -> Unit
) {
    if (plugin != null && isUseListeners)
        Bukkit.getServer().pluginManager.registerEvent(
            T::class.java,
            delegate ?: this,
            priority,
            { _, event -> T::class.safeCast(event)?.block() },
            plugin,
            ignoreCancelled)
}
fun QuestListener.unregister() = HandlerList.unregisterAll(delegate ?: this)