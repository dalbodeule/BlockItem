package space.mori.blockitem

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import space.mori.blockitem.util.getColored

object EventHandler: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerClickedInventory(event: InventoryClickEvent) {
        if (event.slotType == InventoryType.SlotType.ARMOR) {
            val item = event.currentItem?.data?.itemType

            if (item != null) {
                if (!event.whoClicked.hasPermission("blockitem.${item.name.toLowerCase()}")) {
                    event.isCancelled = true
                    event.whoClicked.sendMessage("&4[!]&rYou cannot use item ${item.name.toLowerCase()}".getColored)
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerUseBannedItemWithRightAndLeftClick(event: PlayerInteractEvent) {
        if (event.hasItem()) {
            val item = event.item?.data?.itemType

            if (item != null) {
                if (item in tools || item in armor) {
                    if (!event.player.hasPermission("blockitem.${item.name.toLowerCase()}")) {
                        event.isCancelled = true
                        event.player.sendMessage("&4[!]&rYou cannot use item ${item.name.toLowerCase()}".getColored)
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerUseBowOrCrossbow(event: EntityShootBowEvent) {
        if (event.entityType == EntityType.PLAYER) {
            val item = event.bow?.data?.itemType

            if (item != null) {
                if (item in tools) {
                    if (!event.entity.hasPermission("blockitem.${item.name.toLowerCase()}")) {
                        event.isCancelled = true
                        event.entity.sendMessage("&4[!]&rYou cannot use item ${item.name.toLowerCase()}".getColored)
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerUseBannedItemWithCombat(event: EntityDamageByEntityEvent) {
        if (event.damager.type == EntityType.PLAYER) {
            val item = (event.damager as Player).inventory.itemInMainHand.type

            if (item in tools) {
                if (!event.damager.hasPermission("blockitem.${item.name.toLowerCase()}")) {
                    event.damage = 1.0
                    event.damager.sendMessage("&4[!]&rYou cannot use item ${item.name.toLowerCase()}".getColored)
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
    Material.DIAMOND_SWORD,
    Material.BOW,
    Material.CROSSBOW
)

val armor = listOf(
    Material.LEATHER_BOOTS,
    Material.LEATHER_CHESTPLATE,
    Material.LEATHER_HELMET,
    Material.LEATHER_LEGGINGS,
    Material.CHAINMAIL_BOOTS,
    Material.CHAINMAIL_CHESTPLATE,
    Material.CHAINMAIL_HELMET,
    Material.CHAINMAIL_LEGGINGS,
    Material.IRON_BOOTS,
    Material.IRON_CHESTPLATE,
    Material.IRON_HELMET,
    Material.IRON_LEGGINGS,
    Material.GOLDEN_BOOTS,
    Material.GOLDEN_CHESTPLATE,
    Material.GOLDEN_HELMET,
    Material.GOLDEN_LEGGINGS,
    Material.DIAMOND_BOOTS,
    Material.DIAMOND_CHESTPLATE,
    Material.DIAMOND_HELMET,
    Material.DIAMOND_LEGGINGS,
    Material.ELYTRA
)