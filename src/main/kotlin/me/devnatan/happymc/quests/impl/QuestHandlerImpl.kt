package me.devnatan.happymc.quests.impl

import me.devnatan.happymc.quests.api.handler.QuestEventBlock
import me.devnatan.happymc.quests.api.handler.QuestHandler

open class QuestHandlerImpl : QuestHandler {

    override var start: QuestEventBlock? = null
    override var stop: QuestEventBlock? = null
    override var complete: QuestEventBlock? = null
    override var fail: QuestEventBlock? = null
    override var progress: QuestEventBlock? = null

}