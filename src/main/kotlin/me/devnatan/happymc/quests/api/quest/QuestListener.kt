package me.devnatan.happymc.quests.api.quest

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

interface QuestListener : Listener {

    var plugin: Plugin?

    var delegate: QuestListener?

    var isUseListeners: Boolean

}