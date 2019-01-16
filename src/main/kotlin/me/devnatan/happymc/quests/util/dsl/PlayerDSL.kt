package me.devnatan.happymc.quests.util.dsl

import me.devnatan.happymc.quests.util.TitleAPI
import org.bukkit.entity.Player

fun Player.sendTitle(title: String? = null, subtitle: String? = null) {
    TitleAPI.sendTitle(this, title, subtitle)
}

fun Player.sendTitle(title: String? = null, subtitle: String? = null, fadeIn: Int = 20, stay: Int = 20, fadeOut: Int = 20) {
    TitleAPI.sendTitle(this, title, subtitle, fadeIn, stay, fadeOut)
}