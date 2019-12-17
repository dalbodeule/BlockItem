package space.mori.blockitem.util

import org.bukkit.ChatColor

val String.getColored
  get() = ChatColor.translateAlternateColorCodes('&', this)