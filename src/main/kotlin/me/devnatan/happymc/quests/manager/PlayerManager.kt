package me.devnatan.happymc.quests.manager

import me.devnatan.happymc.quests.api.quest.Quest
import me.devnatan.happymc.quests.plugin.HappyQuests
import java.util.*

class PlayerManager(private val plugin: HappyQuests) {

    private val players: MutableMap<UUID, MutableSet<Quest>> = mutableMapOf()

    fun new(uuid: UUID) {
        if (!players.containsKey(uuid))
            players[uuid] = mutableSetOf()
    }

    fun add(uuid: UUID, quest: Quest) {
        if (!players.containsKey(uuid))
            throw IllegalArgumentException()

        val set = players[uuid]!!
        if (!set.contains(quest)) {
            set.add(quest)
            players[uuid] = set
        }
    }

    operator fun plusAssign(uuid: UUID) {
        new(uuid)
    }

    operator fun plusAssign(pair: Pair<UUID, Quest>) {
        add(pair.first, pair.second)
    }

}