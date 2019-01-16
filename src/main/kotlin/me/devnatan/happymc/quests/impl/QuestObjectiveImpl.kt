package me.devnatan.happymc.quests.impl

import me.devnatan.happymc.quests.api.QuestListener
import me.devnatan.happymc.quests.api.QuestListenerEventBlock
import me.devnatan.happymc.quests.api.objective.QuestObjective

open class QuestObjectiveImpl(
        override val index: Int = 1,
        override val name: String = "Quest Objective",
        override var progressFrom: Int = 0,
        override var progressTo: Int = progressFrom + 1
) : QuestObjective, QuestHandlerImpl() {

    override var isComplete: Boolean = false

    override var isUseListeners: Boolean = true

    override var active: Boolean = false

    override var delegate: QuestListener? = null

    override var events: MutableList<QuestListenerEventBlock> = mutableListOf()

}