package me.devnatan.happymc.quests.api.task

import me.devnatan.happymc.quests.api.QuestExecutor
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

object QuestSchedulerController {

    private val schedulers: MutableMap<Plugin, MutableList<QuestScheduler>> = mutableMapOf()

    fun setup(scheduler: QuestScheduler): BukkitScheduler {
        if (schedulers.containsKey(scheduler.owner)) {
            val schedulers = this.schedulers[scheduler.owner]
            if (schedulers!!.contains(scheduler)) {
                scheduler.owner.logger.info("Trying to get cached scheduler")
                return scheduler.scheduler
            }
            else {
                schedulers.add(scheduler)
                this.schedulers[scheduler.owner] = schedulers
            }
        } else schedulers[scheduler.owner] = mutableListOf(scheduler)

        scheduler.owner.logger.info("New non-cached scheduler registered")
        return Bukkit.getScheduler()
    }

    fun cancelAll(handler: QuestExecutor? = null) {
        if (handler == null) {
            schedulers.forEach {
                it.value.forEach { scheduler ->
                    scheduler.cancelAll()
                }
            }
        } else {
            schedulers.forEach {
                it.value.filter {
                    t -> (t.handler != null) && t.handler == handler
                }.forEach { scheduler ->
                    scheduler.cancelAll()
                }
            }
        }
    }

}

data class QuestScheduler(val owner: Plugin, val handler: QuestExecutor? = null) {

    val scheduler: BukkitScheduler = QuestSchedulerController.setup(this)
    private val tasks: Array<QuestTask> = arrayOf()

    fun cancelAll() {
        tasks.forEach {
            it.cancel()
        }
    }

    inline fun sync(crossinline block: QuestTask.() -> Unit): QuestTask =
            QuestTask().apply {
                start(scheduler.runTask(owner) {
                    block(this)
                })
            }

    inline fun async(crossinline block: QuestTask.() -> Unit): QuestTask =
            QuestTask().apply {
                start(scheduler.runTaskAsynchronously(owner) {
                    block(this)
                })
            }

    inline fun task(interval: Long = 20L, crossinline block: QuestTask.() -> Unit): QuestTask =
            QuestTask().apply {
                start(scheduler.runTaskTimerAsynchronously(owner, {
                    if ((handler != null) && !handler.isActive) {
                        cancel()
                    } else
                        block(this)
                }, interval, interval))
            }

    inline fun syncTask(interval: Long = 20L, crossinline block: QuestTask.() -> Unit) =
            QuestTask().apply {
                start(scheduler.runTaskTimer(owner, {
                    block(this)
                }, interval, interval))
            }

    inline fun laterTask(delay: Long = 20L, crossinline block: QuestTask.() -> Unit): QuestTask =
            QuestTask().apply {
                start(scheduler.runTaskLaterAsynchronously(owner, {
                    block(this)
                }, delay))
            }

    inline fun syncLaterTask(delay: Long = 20L, interval: Long = 20L, crossinline block: QuestTask.() -> Unit): QuestTask =
            QuestTask().apply {
                start(scheduler.runTaskLater(owner, {
                    block(this)
                }, delay))
            }

}

fun scheduler(handler: QuestExecutor? = null, plugin: Plugin, block: QuestScheduler.() -> Unit) = block(QuestScheduler(plugin, handler))