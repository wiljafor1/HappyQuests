package me.devnatan.happymc.quests.api

import me.devnatan.happymc.quests.api.event.QuestEvent

@FunctionalInterface
interface QuestCompletable {

    var isComplete: Boolean

    var isActive: Boolean

    fun progress(event: QuestEvent)

    fun complete(event: QuestEvent)

    fun next(event: QuestEvent)

    fun hasNext(): Boolean

    fun start(event: QuestEvent)

    fun finish(event: QuestEvent)

}

fun QuestCompletable.isComplete() = isComplete

fun QuestCompletable.isNotComplete() = !isComplete

fun QuestCompletable.ifNotComplete(block: QuestCompletable.() -> Unit): QuestCompletable {
    if (isNotComplete()) block()
    return this
}

fun QuestCompletable.whenComplete(block: QuestCompletable.() -> Unit): QuestCompletable {
    if (!isNotComplete()) block()
    return this
}

fun QuestCompletable.ifNotActive(block: QuestCompletable.() -> Unit): QuestCompletable {
    if (!isActive) block()
    return this
}

fun QuestCompletable.whenActive(block: QuestCompletable.() -> Unit): QuestCompletable {
    if (isActive) block()
    return this
}