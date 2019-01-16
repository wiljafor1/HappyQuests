package me.devnatan.happymc.quests.manager

import me.devnatan.happymc.quests.api.Quest
import me.devnatan.happymc.quests.plugin.HappyQuests
import me.devnatan.happymc.quests.plugin.unregister

class QuestManager(private val plugin: HappyQuests) {

    val quests: MutableSet<Quest> = mutableSetOf()

    fun add(quest: Quest) {
        quests.add(quest)
    }

    fun registerAll() {
        quests.forEach {
            it.call(plugin)
            plugin.logger.info("Quest \"${it.name}\" with ${it.objectives.size} objectives registered.")
        }
    }

    fun unregisterAll() {
        plugin.logger.info("All quests have been unregistered.")
        quests.forEach {
            it.unregister()
        }
        quests.clear()
    }

    operator fun plusAssign(quest: Quest) {
        add(quest)
    }

}