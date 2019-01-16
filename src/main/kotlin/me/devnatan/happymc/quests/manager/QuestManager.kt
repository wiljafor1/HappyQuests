package me.devnatan.happymc.quests.manager

import me.devnatan.happymc.quests.api.quest.Quest
import me.devnatan.happymc.quests.plugin.HappyQuests

class QuestManager(private val plugin: HappyQuests) {

    val quests: MutableSet<Quest> = mutableSetOf()

    fun register(quest: Quest) = quests.add(quest)

    fun registerAll() {
        quests.forEach {
            plugin.logger.info("Quest \"${it.name}\" with ${it.objectives.size} objectives registered.")
        }
    }

    fun unregisterAll() {
        plugin.logger.info("All quests have been unregistered.")
        quests.clear()
    }

    operator fun plusAssign(quest: Quest) {
        register(quest)
    }

}