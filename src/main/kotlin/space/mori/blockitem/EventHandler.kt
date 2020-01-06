package space.mori.blockitem

import com.codingforcookies.armorequip.ArmorEquipEvent
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.PotionMeta
import space.mori.blockitem.util.getColored

object EventHandler: Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    internal fun onPlayerUseBannedItemWithRightClick(event: PlayerInteractEvent) {
        val item = event.item?.data?.itemType
        val potionItem = event.item

        if (item != null && potionItem != null) {
            if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
                if (Potion.contains(getItemName(item))) {
                    val potionName = (potionItem.itemMeta as PotionMeta).basePotionData.type

                    if (!event.player.hasPermission("blockitem.potion.$potionName")) {
                        event.isCancelled = true
                        event.player.sendMessage("&4[!]&r You cannot use potion $potionName".getColored)

                        return
                    }

                    val customEffects = (potionItem.itemMeta as PotionMeta).customEffects

                    if (customEffects.size > 0) {
                        customEffects.forEach {
                            if (!event.player.hasPermission("blockitem.potion.${it.type}")) {
                                event.isCancelled = true
                                event.player.sendMessage("&4[!]&r You cannot use potion $potionName".getColored)

                                return
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    internal fun onArmorEquip(event: ArmorEquipEvent) {
        val item = event.newArmorPiece?.data?.itemType

        if (item != null) {
            if (Armor.contains(getItemName(item))) {
                if (!event.player.hasPermission("blockitem.armor.${getItemName(item)}")) {
                    event.isCancelled = true
                    event.player.sendMessage("&4[!]&r You cannot use item ${getItemName(item)}".getColored)
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