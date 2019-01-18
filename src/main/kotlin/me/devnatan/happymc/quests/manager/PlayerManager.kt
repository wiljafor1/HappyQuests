package me.devnatan.happymc.quests.manager

import me.devnatan.happymc.quests.api.Quest
import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.questEvent
import me.devnatan.happymc.quests.plugin.HappyQuests
import org.bukkit.entity.HumanEntity
import java.util.*

class PlayerManager(private val plugin: HappyQuests) {

    private val players: MutableMap<UUID, MutableSet<Quest>> = mutableMapOf()

    fun find(actor: HumanEntity, quest: String, and: Quest.() -> Boolean = { true }): Quest? {
        if (!players.containsKey(actor.uniqueId))
            return null

        val set = players[actor.uniqueId]!!
        if (set.isEmpty())
            return null

        val qst = set.find { it.name == quest }
        if ((qst != null) && and(qst))
            return qst
        return null
    }

    fun new(actor: HumanEntity) {
        if (!players.containsKey(actor.uniqueId))
            players[actor.uniqueId] = mutableSetOf()
    }

    fun add(actor: HumanEntity, quest: Quest?) {
        if (quest == null) return
        if (!players.containsKey(actor.uniqueId))
            throw IllegalArgumentException()

        val set = players[actor.uniqueId]!!
        if (!set.contains(quest)) {
            set.add(quest)
            players[actor.uniqueId] = set
            if (set.size == 1) {
                quest.start(quest.questEvent(actor))
                plugin.logger.info("Quest ${quest.name} started to ${actor.name}.")
            }
        }
    }

    fun complete(actor: HumanEntity, event: QuestEvent) {
        if (!players.containsKey(actor.uniqueId))
            throw IllegalArgumentException()

        val set = players[actor.uniqueId]!!
        if (!set.contains(event.quest))
            throw IllegalStateException()

        set.find {
            it.name == event.quest.name
        }?.let {
            if (it.isComplete)
                throw IllegalStateException("Quest already is complete")
        } ?: throw NullPointerException()

    }

    operator fun plusAssign(actor: HumanEntity) {
        new(actor)
    }

    operator fun plusAssign(pair: Pair<HumanEntity, Quest?>) {
        add(pair.first, pair.second)
    }

}