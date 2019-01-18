# HappyQuests
🏁 Objectives and missions to be completed by players.

## Creating quickly (DSL)
```kotlin
/**
 * When using QuestDSL you will not register it, just create it.
 * To register, you must create your own quests manager.
 */
quest("My quest: join and jump") {
    onComplete {
        actor.sendMessage("All objectives has been complete!")
    }
    
    // The first goal is automatically set as the quest's active target.
    objective {
        onComplete {
            actor.sendMessage("First objective complete, joined the server!")
        }
        
        // You can add Bukkit events directly to a Quest or objective!
        event<PlayerJoinEvent> {
            // If quest and objective is active
            ifActive {

                /**
                 * If complete, the current goal will be set to the next,
                 * if there is not one the next quest will be complete.
                 */
                progress(questEvent(player))
            }
        }
    }

    objective {
        onComplete {
            actor.sendMessage("Second objective complete, jumped!")
        }
        
        event<PlayerMoveEvent> {
            if (jumped) {
                ifActive {
                    progress(questEvent(player))
                }
            }
        }
    }
}
```

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
