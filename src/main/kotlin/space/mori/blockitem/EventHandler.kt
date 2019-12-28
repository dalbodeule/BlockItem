package space.mori.blockitem

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.PotionMeta
import space.mori.blockitem.util.getColored

object EventHandler: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerClickedInventory(event: InventoryClickEvent) {
        val slotItem = event.currentItem?.data?.itemType
        val heldItem = event.cursor?.data?.itemType

        if (slotItem != null && heldItem != null && event.click == ClickType.LEFT) {
            if (getItemName(heldItem) in Armor) {
                if (event.slotType == InventoryType.SlotType.ARMOR && getItemName(slotItem) == "air") {
                    if (!event.whoClicked.hasPermission("blockitem.${getItemName(heldItem)}")) {
                        event.whoClicked.sendMessage("&4[!]&r You cannot use item ${getItemName(heldItem)}".getColored)
                        event.isCancelled = true
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    internal fun onPlayerUseBannedItemWithRightClick(event: PlayerInteractEvent) {
        if (event.hasItem()) {
            val item = event.item?.data?.itemType
            val potionItem = event.item

            if (
                item != null && potionItem != null &&
                (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)
            ) {
                when {
                    getItemName(item) in Potion -> {
                        val potionName = (potionItem.itemMeta as PotionMeta).basePotionData.type.name.toLowerCase()

                        if (!event.player.hasPermission("blockitem.armor.$potionName")) {
                            event.isCancelled = true
                            event.player.sendMessage("&4[!]&r You cannot use potion $potionName".getColored)

                            return
                        }

                        val customEffects = (potionItem.itemMeta as PotionMeta).customEffects

                        if (customEffects.size > 0) {
                            customEffects.forEach {
                                if (!event.player.hasPermission("blockitem.armor.${it.type.name}")) {
                                    event.isCancelled = true
                                    event.player.sendMessage("&4[!]&r You cannot use potion $potionName".getColored)

                                    return
                                }
                            }
                        }
                    }
                    getItemName(item) in Armor -> {
                        if (!event.player.hasPermission("blockitem.armor.${getItemName(item)}")) {
                            event.isCancelled = true
                            event.player.sendMessage("&4[!]&r You cannot use item ${getItemName(item)}".getColored)
                        }
                    }
                }
            }
        }
    }

    private fun getItemName(item: Material): String {
        return item.name.toLowerCase().replace("legacy_", "")
    }
}

val Potion = listOf(
    "potion",
    "splash_potion"
)

val Armor = listOf(
    "leather_boots",
    "leather_chestplate",
    "leather_helmet",
    "leather_leggings",
    "chainmail_boots",
    "chainmail_chestplate",
    "chainmail_helmet",
    "chainmail_leggings",
    "iron_boots",
    "iron_chestplate",
    "iron_helmet",
    "iron_leggings",
    "golden_boots",
    "golden_chestplate",
    "golden_helmet",
    "golden_leggings",
    "diamond_boots",
    "diamond_chestplate",
    "diamond_helmet",
    "diamond_leggings",
    "elytra"
)