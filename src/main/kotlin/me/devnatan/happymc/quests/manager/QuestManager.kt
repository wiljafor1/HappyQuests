package me.devnatan.happymc.quests.manager

import me.devnatan.happymc.quests.api.Quest
import me.devnatan.happymc.quests.api.unregister
import me.devnatan.happymc.quests.plugin.HappyQuests

class QuestManager(private val plugin: HappyQuests) {

    val quests: MutableSet<Quest> = mutableSetOf()

    fun add(quest: Quest) {
        quests.add(quest)
    }

    fun registerAll() {
        quests.forEach {
            it.plugin = plugin
            it.objectives.forEach {
                obj -> obj.plugin = plugin
            }
            it.call()
            plugin.logger.info("Quest \"${it.name}\" with ${it.objectives.size} objectives registered to ${it.plugin!!.name}.")
        }
    }

    fun unregisterAll() {
        plugin.logger.info("All quests have been unregistered.")
        quests.forEach {
            it.unregister()
        }
        quests.clear()
    }

    operator fun get(name: String): Quest? {
        return quests.find { it.name == name }
    }

    operator fun plusAssign(quest: Quest) {
        add(quest)
    }

}