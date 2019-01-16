package me.devnatan.happymc.quests.util.dsl

import org.bukkit.ChatColor

operator fun ChatColor.plus(sequence: CharSequence) = this.toString() + sequence
operator fun String.unaryPlus() =ChatColor.translateAlternateColorCodes('&', this)