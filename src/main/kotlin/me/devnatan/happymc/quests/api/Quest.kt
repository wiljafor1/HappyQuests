package me.devnatan.happymc.quests.api

import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.handler.QuestHandler
import me.devnatan.happymc.quests.api.objective.QuestObjective
import me.devnatan.happymc.quests.util.Named
import org.bukkit.plugin.Plugin

interface Quest : QuestHandler, QuestListener, QuestCompletable, Named {

    var currentObjective: QuestObjective?

    val objectives: MutableList<QuestObjective>

    override fun progress(event: QuestEvent) = throw UnsupportedOperationException()

    override fun complete(event: QuestEvent) {
        if (currentObjective != null && !currentObjective!!.isComplete)
            throw IllegalStateException("Current objective must be complete to finish the quest")

        if (!isComplete) {
            complete?.invoke(event)
            isComplete = true
        }
    }

    override fun call(plugin: Plugin) {
        super.call(plugin)
        objectives.forEach {
            it.call(plugin)
        }
    }

    override fun next(event: QuestEvent) {
        val next = objectives.find { it.index == currentObjective!!.index + 1 }
        if (next == null) {
            complete(event)
        } else {
            next.active = true
            currentObjective = next
        }
    }

    override fun hasNext(): Boolean = objectives.any { it.index == currentObjective!!.index + 1 }

}