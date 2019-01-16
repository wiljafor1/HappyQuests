package me.devnatan.happymc.quests

import me.devnatan.happymc.quests.manager.QuestManager
import me.devnatan.happymc.quests.manager.TaskManager
import org.bukkit.plugin.java.JavaPlugin

class HappyQuests : JavaPlugin() {

    lateinit var questManager: QuestManager
    lateinit var taskManager: TaskManager

    override fun onEnable() {
        managers()
    }

    override fun onDisable() {
        taskManager.interrupt()
    }

    private fun managers() {
        questManager = QuestManager(this)
        taskManager = TaskManager(this)
    }

}