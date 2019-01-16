package me.devnatan.happymc.quests.manager

import me.devnatan.happymc.quests.api.objective.QuestObjectiveAction
import me.devnatan.happymc.quests.api.objective.QuestObjectiveActionSide

object GlobalActionManager {

    private val middlewares: MutableMap<QuestObjectiveAction, QuestObjectiveActionSide> = mutableMapOf()

    fun allBefore() = middlewares.filter { it.value == QuestObjectiveActionSide.BEFORE }.map { it.key }
    fun allAfter() = middlewares.filter { it.value == QuestObjectiveActionSide.AFTER }.map { it.key }
    
    fun addBefore(middleware: QuestObjectiveAction) {
        middlewares[middleware] = QuestObjectiveActionSide.BEFORE
    }

    fun addAfter(middleware: QuestObjectiveAction) {
        middlewares[middleware] = QuestObjectiveActionSide.AFTER
    }

    operator fun plusAssign(middleware: QuestObjectiveAction) {
        addAfter(middleware)
    }
    
    operator fun minusAssign(middleware: QuestObjectiveAction) {
        addBefore(middleware)
    }
    
}