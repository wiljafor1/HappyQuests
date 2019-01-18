package me.devnatan.happymc.quests.api.objective

import me.devnatan.happymc.quests.api.EnumQuestAction
import me.devnatan.happymc.quests.api.EnumQuestActionTarget
import me.devnatan.happymc.quests.api.QuestCompletable
import me.devnatan.happymc.quests.api.QuestListener
import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.handler.QuestHandler
import me.devnatan.happymc.quests.manager.GlobalActionManager
import me.devnatan.happymc.quests.util.Named

interface QuestObjective : QuestHandler, QuestListener, QuestCompletable, Named {

    val index: Int

    var progressFrom: Int

    var progressTo: Int

    operator fun component1(): Int = index
    operator fun component2(): String = name
    operator fun component3(): Int = progressTo - progressFrom

    override fun start(event: QuestEvent) {
        if (isActive || isComplete)
            throw IllegalStateException("Objective cannot be isActive again")

        isActive = true
        start?.invoke(event)
        GlobalActionManager.all({
            it == EnumQuestAction.START
        }, {
            it == EnumQuestActionTarget.OBJECTIVE
        }).forEach {
            it(event)
        }
    }

    override fun finish(event: QuestEvent) {
        if (!isActive)
            throw IllegalStateException("Objective must be isActive")

        fail?.invoke(event)
        isComplete = false
        isActive = false
        GlobalActionManager.all({
            it == EnumQuestAction.FAIL
        }, {
            it == EnumQuestActionTarget.OBJECTIVE
        }).forEach {
            it(event)
        }
    }

    override fun progress(event: QuestEvent) {
        progressFrom++
        if (!isComplete && progressFrom >= progressTo) {
            complete(event)
        } else {
            progress?.invoke(event)
            GlobalActionManager.all({
                it == EnumQuestAction.PROGRESS
            }, {
                it == EnumQuestActionTarget.OBJECTIVE
            }).forEach {
                it(event)
            }
        }
    }

    override fun complete(event: QuestEvent) {
        if (isComplete)
            throw IllegalStateException("Cannot complete the objective one more time")

        isComplete = true
        isActive = false
        complete?.invoke(event)
        event.quest.next(event)
        GlobalActionManager.all({
            it == EnumQuestAction.COMPLETE
        }, {
            it == EnumQuestActionTarget.OBJECTIVE
        }).forEach {
            it(event)
        }
    }

    override fun next(event: QuestEvent) = throw UnsupportedOperationException()
    override fun hasNext(): Boolean = false

}