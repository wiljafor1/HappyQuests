package me.devnatan.happymc.quests.manager

import me.devnatan.happymc.quests.api.EnumQuestAction
import me.devnatan.happymc.quests.api.EnumQuestActionTarget
import me.devnatan.happymc.quests.api.QuestAction

object GlobalActionManager {

    private val actions: MutableMap<QuestAction, Pair<Array<out EnumQuestAction>, Array<out EnumQuestActionTarget>>> = mutableMapOf()

    fun all(predicate1: (EnumQuestAction) -> Boolean, predicate2: (EnumQuestActionTarget) -> Boolean): Array<QuestAction> {
        return actions.entries.filter {
            (it.value.first.all(predicate1) or it.value.first.any {
                act -> act == EnumQuestAction.ALL
            }) and (it.value.second.any(predicate2) or it.value.second.any {
                tgt -> tgt == EnumQuestActionTarget.ALL
            })
        }.map {
            it.key
        }.toTypedArray()
    }

    internal fun add(action: QuestAction,
                     targets: Array<out EnumQuestActionTarget> = arrayOf(EnumQuestActionTarget.ALL),
                     actions: Array<out EnumQuestAction> = arrayOf(EnumQuestAction.ALL)) {
        if (actions.isNotEmpty())
            this.actions[action] = actions to targets
    }

    fun action(action: QuestAction) {
        GlobalActionManager.add(action)
    }

}

class QuestActionBody {

    private fun on(target: EnumQuestActionTarget, vararg actions: EnumQuestAction = arrayOf(), block: QuestAction) {
        GlobalActionManager.add(block, arrayOf(target), actions.toList().toTypedArray())
    }

    fun onQuest(block: QuestAction): Unit = on(EnumQuestActionTarget.QUEST, EnumQuestAction.ALL, block = block)
    fun onQuestStart(block: QuestAction): Unit = on(EnumQuestActionTarget.QUEST, EnumQuestAction.START, block = block)
    fun onQuestFail(block: QuestAction): Unit = on(EnumQuestActionTarget.QUEST, EnumQuestAction.FAIL, block = block)
    fun onQuestProgress(block: QuestAction): Unit = on(EnumQuestActionTarget.QUEST, EnumQuestAction.PROGRESS, block = block)
    fun onQuestComplete(block: QuestAction): Unit = on(EnumQuestActionTarget.QUEST, EnumQuestAction.COMPLETE, block = block)

    fun onObjective(block: QuestAction): Unit = on(EnumQuestActionTarget.OBJECTIVE, EnumQuestAction.ALL, block = block)
    fun onObjectiveStart(block: QuestAction): Unit = on(EnumQuestActionTarget.OBJECTIVE, EnumQuestAction.START, block = block)
    fun onObjectiveFail(block: QuestAction): Unit = on(EnumQuestActionTarget.OBJECTIVE, EnumQuestAction.FAIL, block = block)
    fun onObjectiveProgress(block: QuestAction): Unit = on(EnumQuestActionTarget.OBJECTIVE, EnumQuestAction.PROGRESS, block = block)
    fun onObjectiveComplete(block: QuestAction): Unit = on(EnumQuestActionTarget.OBJECTIVE, EnumQuestAction.COMPLETE, block = block)


}

fun action(block: QuestActionBody.() -> Unit) {
    block(QuestActionBody())
}