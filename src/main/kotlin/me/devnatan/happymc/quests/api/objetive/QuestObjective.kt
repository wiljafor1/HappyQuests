package me.devnatan.happymc.quests.api.objetive

import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.handler.QuestHandler
import me.devnatan.happymc.quests.api.quest.QuestCompletable
import me.devnatan.happymc.quests.api.quest.QuestListener

interface QuestObjective : QuestHandler, QuestListener, QuestCompletable {

    val index: Int

    val name: String

    var progressFrom: Int

    var progressTo: Int

    var next: QuestObjective?

    var active: Boolean

    override fun progress(event: QuestEvent) {
        progressFrom++
        if (isComplete)
            complete(event)
        else progress?.invoke(event)
    }

    override fun complete(event: QuestEvent) {
        if (isComplete)
            throw IllegalStateException("Cannot complete the objective one more time")

        isComplete = true
        if (next != null) {
            next!!.active = true
            event.quest.currentObjective = next
        } else event.quest.complete(event)
        active = false
    }

}