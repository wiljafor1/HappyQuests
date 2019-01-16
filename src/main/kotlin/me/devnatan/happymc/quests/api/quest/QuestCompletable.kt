package me.devnatan.happymc.quests.api.quest

import me.devnatan.happymc.quests.api.event.QuestEvent

@FunctionalInterface
interface QuestCompletable {

    var isComplete: Boolean

    fun progress(event: QuestEvent)

    fun complete(event: QuestEvent)

}