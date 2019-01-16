package me.devnatan.happymc.quests.api.event

import me.devnatan.happymc.quests.api.objetive.QuestObjective
import me.devnatan.happymc.quests.api.quest.Quest
import org.bukkit.entity.LivingEntity

class QuestEvent(val actor: LivingEntity,
        val quest: Quest,
        val objective: QuestObjective,
        val time: Long)