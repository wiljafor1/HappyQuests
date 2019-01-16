package me.devnatan.happymc.quests.plugin

import me.devnatan.happymc.quests.manager.PlayerManager
import me.devnatan.happymc.quests.manager.QuestManager
import me.devnatan.happymc.quests.manager.TaskManager
import me.devnatan.happymc.quests.plugin.quests.BedQuest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class HappyQuests : JavaPlugin() {

    lateinit var questManager: QuestManager
    lateinit var taskManager: TaskManager
    lateinit var playerManager: PlayerManager

    override fun onEnable() {
        managers()
        quests()
        players()
        tasks()
    }

    override fun onDisable() {
        questManager.unregisterAll()
        taskManager.interrupt()
    }

    private fun managers() {
        questManager = QuestManager(this)
        taskManager = TaskManager(this)
        playerManager = PlayerManager(this)
    }

    private fun quests() {
        questManager += BedQuest()
    }

    private fun players() {
        server.pluginManager.registerEvents(object: Listener {
            @EventHandler fun on(e: PlayerJoinEvent) {
                playerManager += e.player.uniqueId
                playerManager += e.player.uniqueId to BedQuest()
            }
        }, this)
    }

    private fun tasks() {
        taskManager.start()
    }

}