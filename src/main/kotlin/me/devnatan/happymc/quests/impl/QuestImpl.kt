package me.devnatan.happymc.quests.impl

import me.devnatan.happymc.quests.api.Quest
import me.devnatan.happymc.quests.api.QuestListener
import me.devnatan.happymc.quests.api.QuestListenerEventBlock
import me.devnatan.happymc.quests.api.objective.QuestObjective

open class QuestImpl(override val name: String) : Quest, QuestHandlerImpl() {

    override var currentObjective: QuestObjective? = null
    override val objectives: MutableList<QuestObjective> = mutableListOf()
    override var isComplete: Boolean = false
    override var isUseListeners: Boolean = true
    override var delegate: QuestListener? = null
    override var events: MutableList<QuestListenerEventBlock> = mutableListOf()

    fun withCurrentObjective(objective: QuestObjective?): Quest {
        if (currentObjective != null)
            throw IllegalStateException("Current objective is already defined")

        currentObjective = objective
        if (objective != null) {
            objective.active = true
            withObjective(objective)
        }
        return this
    }

    fun withObjective(objective: QuestObjective): Quest {
        objective.delegate = this
        objectives.add(objective)
        return this
    }

}