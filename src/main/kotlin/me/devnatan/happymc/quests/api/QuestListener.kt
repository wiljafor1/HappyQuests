package me.devnatan.happymc.quests.api

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

typealias QuestListenerEventBlock = (plugin: Plugin) -> Unit

interface QuestListener : Listener {

    var isUseListeners: Boolean

    var delegate: QuestListener?

    var events: MutableList<QuestListenerEventBlock>

    fun register(block: QuestListenerEventBlock) {
        events.add(block)
    }

    fun call(plugin: Plugin) {
        val iterator = events.iterator()
        while (iterator.hasNext()) {
            iterator.next()(plugin)
            iterator.remove()
        }
    }

}