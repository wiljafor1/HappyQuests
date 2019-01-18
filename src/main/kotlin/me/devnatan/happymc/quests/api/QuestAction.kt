package me.devnatan.happymc.quests.api

import me.devnatan.happymc.quests.api.event.QuestEvent

typealias QuestAction = QuestEvent.() -> Unit

enum class EnumQuestAction {

    ALL, START, COMPLETE, PROGRESS, FAIL

}

enum class EnumQuestActionTarget {

    ALL, QUEST, OBJECTIVE

}