package space.mori.blockitem

import org.bukkit.plugin.java.JavaPlugin

class BlockItem: JavaPlugin() {
    companion object {
        lateinit var instance: BlockItem
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(EventHandler, this)

        instance = this
    }

    override fun onDisable() {

    }
}