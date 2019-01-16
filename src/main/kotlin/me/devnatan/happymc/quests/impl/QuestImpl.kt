package me.devnatan.happymc.quests.impl

import me.devnatan.happymc.quests.api.objetive.QuestObjective
import me.devnatan.happymc.quests.api.quest.Quest
import me.devnatan.happymc.quests.api.quest.QuestListener
import org.bukkit.plugin.Plugin

open class QuestImpl(override val name: String) : Quest, QuestHandlerImpl() {

    override var currentObjective: QuestObjective? = null

    override val objectives: MutableList<QuestObjective> = mutableListOf()

    override var isComplete: Boolean = false

    override var plugin: Plugin? = null

    override var isUseListeners: Boolean = true

    override var delegate: QuestListener? = null

}