package me.devnatan.happymc.quests.util.dsl

import org.bukkit.event.player.PlayerMoveEvent

val PlayerMoveEvent.moved: Boolean
    get() = walked || jumped || looked

val PlayerMoveEvent.walked: Boolean
    get() = from.x != this.to.x || this.from.z != this.to.z

val PlayerMoveEvent.jumped: Boolean
    get() = this.from.y != this.to.y

val PlayerMoveEvent.looked: Boolean
    get() = this.from.yaw != this.to.yaw || this.from.pitch != this.to.pitch