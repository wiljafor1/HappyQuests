package me.devnatan.happymc.quests.plugin

import me.devnatan.happymc.quests.api.event
import me.devnatan.happymc.quests.api.listener
import me.devnatan.happymc.quests.api.task.QuestSchedulerController
import me.devnatan.happymc.quests.manager.PlayerManager
import me.devnatan.happymc.quests.manager.QuestManager
import me.devnatan.happymc.quests.manager.action
import me.devnatan.happymc.quests.plugin.quests.TutorialQuest
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class HappyQuests : JavaPlugin() {

    lateinit var questManager: QuestManager
    lateinit var playerManager: PlayerManager

    override fun onEnable() {
        managers()
        quests()
        actions()
        players()
    }

    override fun onDisable() {
        questManager.unregisterAll()
        QuestSchedulerController.cancelAll()
    }

    private fun managers() {
        questManager = QuestManager(this)
        playerManager = PlayerManager(this)
    }

    private fun quests() {
        questManager += TutorialQuest()
        questManager.registerAll()
    }

    private fun actions() {
        action {
            onObjectiveComplete {
                actor.sendMessage("Objective complete!")
            }

            onQuestComplete {
                actor.sendMessage("Quest complete!")
            }
        }
    }

    private fun players() {
        listener(this) {
            event<PlayerJoinEvent> {
                player.sendMessage("joined")
                playerManager += player
                playerManager += player to questManager["Tutorial"]
            }

            event<PlayerBedEnterEvent> {
                playerManager.find(player, "Tutorial") {
                    isActive && !isComplete
                }?.let {
                    it.complete(player)
                }
            }
        }
    }

}