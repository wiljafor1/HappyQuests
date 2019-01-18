package me.devnatan.happymc.quests.plugin.quests

import me.devnatan.happymc.quests.api.event
import me.devnatan.happymc.quests.api.impl.QuestImpl
import org.bukkit.event.player.PlayerBedEnterEvent

internal class TutorialQuest : QuestImpl("Tutorial")  {

    init {
        onStart {
            scheduler {
                task {
                    actor.sendMessage("Task assíncrona rodando,")
                    actor.sendMessage("esperando você completar a quest.")
                }
            }

            event<PlayerBedEnterEvent> {
                player.sendMessage("Legal, deitou-se, muito bem!")
                complete(player)
            }
        }

        onComplete {
            actor.sendMessage("Tutorial completo")
        }
    }


}