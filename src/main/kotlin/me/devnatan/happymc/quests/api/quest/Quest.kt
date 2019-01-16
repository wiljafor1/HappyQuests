package me.devnatan.happymc.quests.api.quest

import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.handler.QuestHandler
import me.devnatan.happymc.quests.api.objetive.QuestObjective

interface Quest : QuestHandler, QuestListener, QuestCompletable {

    val name: String

    var currentObjective: QuestObjective?

    val objectives: MutableList<QuestObjective>

    fun withCurrentObjective(objective: QuestObjective?): Quest {
        if (currentObjective != null)
            throw IllegalStateException("Current objective is already defined")

        currentObjective = objective
        withObjective(objective!!)
        return this
    }

    fun withObjective(objective: QuestObjective): Quest {
        objective.delegate = this
        objectives.add(objective)
        return this
    }

    fun withObjectiveAsCurrentObjective(): Quest {
        return if (currentObjective == null) this
        else withObjective(currentObjective!!)
    }

    override fun progress(event: QuestEvent) = throw UnsupportedOperationException()

    override fun complete(event: QuestEvent) {
        if (currentObjective != null && !currentObjective!!.isComplete)
            throw IllegalStateException("Current objective must be complete to finish the quest")

        if (!isComplete) {
            complete?.invoke(event)
            isComplete = true
        }
    }

}