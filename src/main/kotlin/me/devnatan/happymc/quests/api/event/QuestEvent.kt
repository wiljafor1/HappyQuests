package me.devnatan.happymc.quests.api.event

import me.devnatan.happymc.quests.api.Quest
import me.devnatan.happymc.quests.api.objective.QuestObjective
import org.bukkit.entity.LivingEntity

class QuestEvent(val actor: LivingEntity, val quest: Quest, val objective: QuestObjective, val time: Long)