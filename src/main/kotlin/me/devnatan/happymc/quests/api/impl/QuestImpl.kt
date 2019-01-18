package me.devnatan.happymc.quests.api.impl

import me.devnatan.happymc.quests.api.Quest
import me.devnatan.happymc.quests.api.QuestListener
import me.devnatan.happymc.quests.api.QuestListenerEventBlock
import me.devnatan.happymc.quests.api.objective.QuestObjective
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

open class QuestImpl(override val name: String) : Quest, QuestHandlerImpl() {

    override var currentObjective: QuestObjective? = null
    override val objectives: MutableList<QuestObjective> = mutableListOf()
    override var isComplete: Boolean = false
    override var isUseListeners: Boolean = true
    override var delegate: QuestListener? = null
    override var events: MutableList<QuestListenerEventBlock> = mutableListOf()
    override var isActive: Boolean = false
    override var plugin: Plugin? = null
    override var playerData: ConcurrentMap<UUID, Pair<String, Any?>> = ConcurrentHashMap()

    fun withObjective(objective: QuestObjective): Quest {
        if (objectives.isEmpty() || objective.index == 1)
            currentObjective = objective
        objective.delegate = this
        objectives.add(objective)
        return this
    }

}

class QuestImplDerived(impl: QuestImpl, block: Quest.() -> Unit): Quest by impl {

    init { block() }

}