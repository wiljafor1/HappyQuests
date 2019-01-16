package me.devnatan.happymc.quests.api.handler

import me.devnatan.happymc.quests.api.event.QuestEvent

typealias QuestEventBlock = QuestEvent.() -> Unit

interface QuestHandler {

    var start: QuestEventBlock?
    var stop: QuestEventBlock?
    var complete: QuestEventBlock?
    var fail: QuestEventBlock?
    var progress: QuestEventBlock?

    fun onStart(block: QuestEventBlock) {
        start = block
    }

    fun onStop(block: QuestEventBlock) {
        stop = block
    }

    fun onComplete(block: QuestEventBlock) {
        complete = block
    }

    fun onFail(block: QuestEventBlock) {
        fail = block
    }

    fun onProgress(block: QuestEventBlock) {
        progress = block
    }

}