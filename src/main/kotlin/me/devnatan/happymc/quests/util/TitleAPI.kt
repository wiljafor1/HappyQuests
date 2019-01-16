package me.devnatan.happymc.quests.util

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.lang.reflect.Constructor

object TitleAPI {

    fun sendTitle(player: Player,
            title: String? = null,
            subtitle: String? = null,
            fadeIn: Int = 20,
            stay: Int = 20,
            fadeOut: Int = 20) {
        sendTitle(player, fadeIn, stay, fadeOut, title, subtitle)
    }

    private fun sendPacket(player: Player, packet: Any) {
        try {
            val handle = player.javaClass.getMethod("getHandle").invoke(player)
            val playerConnection = handle.javaClass.getField("playerConnection").get(handle)
            playerConnection.javaClass.getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getNMSClass(name: String): Class<*>? {
        val version = Bukkit.getServer().javaClass.getPackage().name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3]
        return try {
            Class.forName("net.minecraft.server.$version.$name")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun sendTitle(player: Player, fadeIn: Int, stay: Int, fadeOut: Int, title: String?, subtitle: String?) {
        var title = title
        var subtitle = subtitle
        try {
            var e: Any
            var chatTitle: Any
            var chatSubtitle: Any
            var subtitleConstructor: Constructor<*>
            var titlePacket: Any
            var subtitlePacket: Any

            if (title != null) {
                title = ChatColor.translateAlternateColorCodes('&', title)
                e = getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0].getField("TIMES").get(null as Any?)
                chatTitle = getNMSClass("IChatBaseComponent")!!.declaredClasses[0].getMethod("a", *arrayOf<Class<*>>(String::class.java)).invoke(null as Any?, *arrayOf<Any>("{\"text\":\"$title\"}"))
                subtitleConstructor = getNMSClass("PacketPlayOutTitle")!!.getConstructor(*arrayOf<Class<*>>(getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0], getNMSClass("IChatBaseComponent")!!, Integer.TYPE, Integer.TYPE, Integer.TYPE))
                titlePacket = subtitleConstructor.newInstance(e, chatTitle, fadeIn, stay, fadeOut)
                sendPacket(player, titlePacket)
                e = getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0].getField("TITLE").get(null as Any?)
                chatTitle = getNMSClass("IChatBaseComponent")!!.declaredClasses[0].getMethod("a", *arrayOf<Class<*>>(String::class.java)).invoke(null as Any?, *arrayOf<Any>("{\"text\":\"$title\"}"))
                subtitleConstructor = getNMSClass("PacketPlayOutTitle")!!.getConstructor(*arrayOf<Class<*>>(getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0], getNMSClass("IChatBaseComponent")!!))
                titlePacket = subtitleConstructor.newInstance(e, chatTitle)
                sendPacket(player, titlePacket)
            }

            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle)
                subtitle = subtitle!!.replace("%player%".toRegex(), player.displayName)
                e = getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0].getField("TIMES").get(null as Any?)
                chatSubtitle = getNMSClass("IChatBaseComponent")!!.declaredClasses[0].getMethod("a", String::class.java).invoke(null as Any?, "{\"text\":\"$title\"}")
                subtitleConstructor = getNMSClass("PacketPlayOutTitle")!!.getConstructor(getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0], getNMSClass("IChatBaseComponent")!!, Integer.TYPE, Integer.TYPE, Integer.TYPE)
                subtitlePacket = subtitleConstructor.newInstance(*arrayOf<Any>(e, chatSubtitle, fadeIn, stay, fadeOut))
                sendPacket(player, subtitlePacket)
                e = getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0].getField("SUBTITLE").get(null as Any?)
                chatSubtitle = getNMSClass("IChatBaseComponent")!!.declaredClasses[0].getMethod("a", String::class.java).invoke(null as Any?, "{\"text\":\"$subtitle\"}")
                subtitleConstructor = getNMSClass("PacketPlayOutTitle")!!.getConstructor(getNMSClass("PacketPlayOutTitle")!!.declaredClasses[0], getNMSClass("IChatBaseComponent")!!, Integer.TYPE, Integer.TYPE, Integer.TYPE)
                subtitlePacket = subtitleConstructor.newInstance(*arrayOf<Any>(e, chatSubtitle, fadeIn, stay, fadeOut))
                sendPacket(player, subtitlePacket)
            }
        } catch (var11: Exception) {
            var11.printStackTrace()
        }

    }

    fun clearTitle(player: Player) {
        sendTitle(player, 0, 0, 0, "", "")
    }

}