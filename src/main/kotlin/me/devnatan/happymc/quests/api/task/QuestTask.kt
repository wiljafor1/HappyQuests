package me.devnatan.happymc.quests.api.task

import org.bukkit.scheduler.BukkitTask

open class QuestTask {

    var running: Boolean = false
    private var task: BukkitTask? = null

    fun start(task: BukkitTask) {
        this.task = task
        this.running = true
        task.owner.logger.info("New task with id ${task.taskId} registered.")
    }

    fun cancel() {
        if (!running) return
        if (task == null) return

        task!!.cancel()
        running = false
        task!!.owner.logger.info("Task ${task!!.taskId} cancelled.")
    }

    operator fun component1(): Int? = task?.taskId ?: -1
    operator fun component2(): Boolean = running

}