package space.mori.blockitem

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent

object EventHandler: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerClickedInventory(event: InventoryClickEvent) {
        if (event.slotType == InventoryType.SlotType.ARMOR) {
            val itemName = event.currentItem.data.itemType.name

            if (!event.whoClicked.hasPermission("blockitem.armor.$itemName")) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerUseBannedItemWithRightClick(event: PlayerInteractEvent) {
        if (event.hasItem()) {
            val itemName = event.item.data.itemType.name
            when {
                itemName == Material.BOW.name -> {
                    if (!event.player.hasPermission("blockitem.use.$itemName")) {
                        event.isCancelled = true
                    }
                }
                tools.contains(itemName) -> {
                    if (!event.player.hasPermission("blockitem.use.$itemName")) {
                        event.isCancelled
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerUseBannedItemWithCombat(event: EntityDamageByEntityEvent) {
        if (event.damager.type == EntityType.PLAYER) {
            val itemName = (event.damager as Player).inventory.itemInMainHand.type.name

            if (tools.contains(itemName)) {
                if (!event.damager.hasPermission("blockitem.use.$itemName")) {
                    event.damage = 1.0
                }
            }
        }
    }
}

val tools = listOf(
    Material.WOODEN_AXE,
    Material.WOODEN_HOE,
    Material.WOODEN_PICKAXE,
    Material.WOODEN_SHOVEL,
    Material.WOODEN_SWORD,
    Material.STONE_AXE,
    Material.STONE_HOE,
    Material.STONE_PICKAXE,
    Material.STONE_SHOVEL,
    Material.STONE_SWORD,
    Material.IRON_AXE,
    Material.IRON_HOE,
    Material.IRON_PICKAXE,
    Material.IRON_SHOVEL,
    Material.IRON_SWORD,
    Material.GOLDEN_AXE,
    Material.GOLDEN_HOE,
    Material.GOLDEN_PICKAXE,
    Material.GOLDEN_SHOVEL,
    Material.GOLDEN_SWORD,
    Material.DIAMOND_AXE,
    Material.DIAMOND_HOE,
    Material.DIAMOND_PICKAXE,
    Material.DIAMOND_SHOVEL,
    Material.DIAMOND_SWORD
).map { it.name }