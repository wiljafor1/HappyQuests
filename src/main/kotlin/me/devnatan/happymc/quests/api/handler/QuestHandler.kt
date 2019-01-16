package me.devnatan.happymc.quests.api.handler

import me.devnatan.happymc.quests.api.objetive.QuestObjective

typealias QuestObjectiveCompleteBlock = QuestObjective.() -> Unit

interface QuestHandler {

    fun onComplete(block: QuestObjectiveCompleteBlock)

}