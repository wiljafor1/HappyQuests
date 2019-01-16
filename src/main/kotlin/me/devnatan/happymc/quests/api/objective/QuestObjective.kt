package me.devnatan.happymc.quests.api.objective

import me.devnatan.happymc.quests.api.QuestCompletable
import me.devnatan.happymc.quests.api.QuestListener
import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.handler.QuestHandler
import me.devnatan.happymc.quests.manager.GlobalActionManager
import me.devnatan.happymc.quests.util.Named

typealias QuestObjectiveAction = QuestEvent.() -> Unit

enum class QuestObjectiveActionSide {
    BEFORE, AFTER
}

interface QuestObjective : QuestHandler, QuestListener, QuestCompletable, Named {

    val index: Int

    var progressFrom: Int

    var progressTo: Int

    var active: Boolean

    override fun progress(event: QuestEvent) {
        progressFrom++
        if (!isComplete && progressFrom >= progressTo) {
            complete(event)
        } else {
            GlobalActionManager.allBefore().forEach { it(event) }
            progress?.invoke(event)
            GlobalActionManager.allAfter().forEach { it(event) }
        }
    }

    override fun complete(event: QuestEvent) {
        if (isComplete)
            throw IllegalStateException("Cannot complete the objective one more time")

        GlobalActionManager.allBefore().forEach { it(event) }
        isComplete = true
        active = false
        complete?.invoke(event)
        event.quest.next(event)
        GlobalActionManager.allAfter().forEach { it(event) }
    }

    override fun next(event: QuestEvent) = throw UnsupportedOperationException()
    override fun hasNext(): Boolean = false

}