# HappyQuests
🏁 Objectives and missions to be completed by players.

## Creating you own quest
```kotlin
import me.devnatan.happymc.quests.plugin.event
import me.devnatan.happymc.quests.plugin.ifActive
import me.devnatan.happymc.quests.plugin.questEvent

class BedQuest : QuestImpl("Sleep hour") {

    init {
        // Register the objetives
        withCurrentObjective(FirstObjective())
        withObjective(SecondObjective())
    }

    inner class FirstObjective : QuestObjectiveImpl(1, "Join bed") {

        init {
            event<PlayerBedEnterEvent> {
                // Only execute if current objective is active
                ifActive {
                    /**
                     * Add progress to the objective.
                     * If current objective is the last and is complete, the quest is complete together. 
                     */
                    progress(questEvent(player))
                }
            }
        }

    }

    inner class SecondObjective : QuestObjectiveImpl(2, "Leave bed") {

        init {
            event<PlayerBedLeaveEvent> {
                ifActive {
                    progress(questEvent(player))
                }
            }
        }

    }

}
```
