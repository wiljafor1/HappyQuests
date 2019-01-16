package me.devnatan.happymc.quests.plugin.quests

import me.devnatan.happymc.quests.impl.QuestImpl
import me.devnatan.happymc.quests.impl.QuestObjectiveImpl
import me.devnatan.happymc.quests.plugin.event
import me.devnatan.happymc.quests.plugin.ifActive
import me.devnatan.happymc.quests.plugin.questEvent
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerBedLeaveEvent

class BedQuest : QuestImpl("Hora da soneca") {

    init {
        withCurrentObjective(FirstObjective())
        withObjective(SecondObjective())
    }

    inner class FirstObjective : QuestObjectiveImpl(1, "Entrar na cama") {

        init {
            event<PlayerBedEnterEvent> {
                ifActive {
                    progress(questEvent(player))
                }
            }
        }

    }

    inner class SecondObjective : QuestObjectiveImpl(2, "Sair da cama") {

        init {
            event<PlayerBedLeaveEvent> {
                ifActive {
                    progress(questEvent(player))
                }
            }
        }

    }

}