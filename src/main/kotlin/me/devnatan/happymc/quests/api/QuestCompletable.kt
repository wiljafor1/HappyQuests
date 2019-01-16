package me.devnatan.happymc.quests.api

import me.devnatan.happymc.quests.api.event.QuestEvent

@FunctionalInterface
interface QuestCompletable {

    var isComplete: Boolean

    fun progress(event: QuestEvent)

    fun complete(event: QuestEvent)

    fun next(event: QuestEvent)

    fun hasNext(): Boolean

}