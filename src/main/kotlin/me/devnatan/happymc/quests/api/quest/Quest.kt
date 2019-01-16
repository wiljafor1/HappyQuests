package me.devnatan.happymc.quests.api.quest

import me.devnatan.happymc.quests.api.handler.QuestHandler

interface Quest {

    val handlers: Array<in QuestHandler>

}