package me.devnatan.happymc.quests.impl

import me.devnatan.happymc.quests.api.objetive.QuestObjective
import me.devnatan.happymc.quests.api.quest.QuestListener
import org.bukkit.plugin.Plugin

abstract class QuestObjectiveImpl(
        override val index: Int = 1,
        override val name: String = "Quest Objective",
        override var progressFrom: Int = 0,
        override var progressTo: Int = 0,
        override var next: QuestObjective? = null
) : QuestObjective, QuestHandlerImpl() {

    override var isComplete: Boolean = false

    override var plugin: Plugin? = null

    override var isUseListeners: Boolean = true

    override var active: Boolean = false

    override var delegate: QuestListener? = null

}