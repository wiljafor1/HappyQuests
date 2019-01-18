package me.devnatan.happymc.quests.api.impl

import me.devnatan.happymc.quests.api.QuestListener
import me.devnatan.happymc.quests.api.QuestListenerEventBlock
import me.devnatan.happymc.quests.api.objective.QuestObjective
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

open class QuestObjectiveImpl(
        override val index: Int = 1,
        override val name: String = "Quest Objective",
        override var progressFrom: Int = 0,
        override var progressTo: Int = progressFrom + 1
) : QuestObjective, QuestHandlerImpl() {

    override var plugin: Plugin? = null
    override var isComplete: Boolean = false
    override var isUseListeners: Boolean = true
    override var delegate: QuestListener? = null
    override var events: MutableList<QuestListenerEventBlock> = mutableListOf()
    override var isActive: Boolean = false
    override var playerData: ConcurrentMap<UUID, Pair<String, Any?>> = ConcurrentHashMap()

}