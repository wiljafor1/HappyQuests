package me.devnatan.happymc.quests.api

import me.devnatan.happymc.quests.api.event.QuestEvent
import me.devnatan.happymc.quests.api.handler.QuestHandler
import me.devnatan.happymc.quests.api.impl.QuestImpl
import me.devnatan.happymc.quests.api.impl.QuestImplDerived
import me.devnatan.happymc.quests.api.impl.QuestObjectiveImpl
import me.devnatan.happymc.quests.api.objective.QuestObjective
import me.devnatan.happymc.quests.api.task.QuestScheduler
import me.devnatan.happymc.quests.api.task.QuestSchedulerController
import me.devnatan.happymc.quests.util.Named
import me.devnatan.happymc.quests.util.dsl.now
import org.bukkit.entity.HumanEntity

interface QuestExecutor : QuestListener, QuestCompletable {

    fun scheduler(block: QuestScheduler.() -> Unit) {
        if (plugin == null)
            throw NullPointerException("Listener's plugin cannot be null")
        me.devnatan.happymc.quests.api.task.scheduler(this, plugin!!, block)
    }

    fun cancelTasks() {
        QuestSchedulerController.cancelAll(this)
    }

}

interface Quest : QuestHandler, QuestExecutor, Named {

    var currentObjective: QuestObjective?

    val objectives: MutableList<QuestObjective>

    override fun start(event: QuestEvent) {
        if (isActive) throw IllegalStateException("Quest cannot be isActive again")
        if (isComplete) throw IllegalStateException("Quest is complete")

        isActive = true
        start?.invoke(event)
        if (currentObjective != null)
            currentObjective!!.start(event)
    }

    override fun finish(event: QuestEvent) {
        // TODO: ...
    }

    override fun progress(event: QuestEvent) {
        throw UnsupportedOperationException()
    }

    override fun complete(event: QuestEvent) {
        if (!isActive) throw IllegalStateException("Quest isn't isActive to be complete")

        if ((currentObjective != null) && (!currentObjective!!.isComplete || !currentObjective!!.isActive))
            throw IllegalStateException("Current objective must be complete to finish the quest")

        if (!isComplete) {
            complete?.invoke(event)
            isActive = false
            isComplete = true
            unregister()
        }
    }

    override fun call() {
        super.call()
        objectives.forEach {
            it.call()
        }
    }

    override fun next(event: QuestEvent) {
        if (currentObjective == null)
            throw IllegalArgumentException("Nothing to next")

        if (objectives.isEmpty()) {
            complete(event)
            return
        }

        var next: QuestObjective?
        if (currentObjective != null) {
            next = objectives.find {
                it.index == currentObjective!!.index + 1
            }
        } else next = objectives.first()

        if (next == null) {
            complete(event)
        } else {
            next.start(event)
            currentObjective = next
        }
    }

    fun progress(actor: HumanEntity): Unit = progress(questEvent(actor))
    fun complete(actor: HumanEntity): Unit = complete(questEvent(actor))

    override fun hasNext(): Boolean = (objectives.isNotEmpty()) or objectives.any { it.index == currentObjective!!.index + 1 }

    operator fun plusAssign(objective: QuestObjective) { objectives.add(objective) }
    operator fun iterator(): MutableIterator<QuestObjective> = objectives.iterator()
    operator fun contains(objective: QuestObjective): Boolean = objectives.contains(objective)

}

fun quest(name: String, block: Quest.() -> Unit): Quest = QuestImplDerived(QuestImpl(name), block)

@Deprecated("Deprecated")
fun Quest.objective(block: QuestObjective.() -> Unit): QuestObjective {
    val index = objectives.size + 1
    val impl = QuestObjectiveImpl(index, "")
    if (index == 1)
        currentObjective = impl
    block(impl)
    return impl
}

fun Quest.questEvent(entity: HumanEntity): QuestEvent = QuestEvent(entity, this, currentObjective, now())