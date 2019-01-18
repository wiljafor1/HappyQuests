package me.devnatan.happymc.quests.api.handler

import me.devnatan.happymc.quests.api.event.QuestEvent

typealias QuestEventBlock = QuestEvent.() -> Unit

interface QuestHandler {

    var start: QuestEventBlock?
    var fail: QuestEventBlock?
    var complete: QuestEventBlock?
    var progress: QuestEventBlock?

    fun onStart(block: QuestEventBlock) {
        start = block
    }

    fun onFail(block: QuestEventBlock) {
        fail = block
    }

    fun onComplete(block: QuestEventBlock) {
        complete = block
    }

    fun onProgress(block: QuestEventBlock) {
        progress = block
    }

}