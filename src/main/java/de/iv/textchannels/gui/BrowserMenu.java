package de.iv.textchannels.gui;

import de.iv.ILib;
import de.iv.iutils.exceptions.ManagerSetupException;
import de.iv.iutils.exceptions.MenuManagerException;
import de.iv.iutils.items.ItemBuilder;
import de.iv.iutils.menus.InventoryMapper;
import de.iv.iutils.menus.MenuManager;
import de.iv.textchannels.Main;
import de.iv.textchannels.TextChannel;
import de.iv.textchannels.Uni;
import de.iv.textchannels.commands.conversations.TextChannelPasswordConversation;
import de.iv.textchannels.listeners.ConversationAboundedListener;
import de.iv.textchannels.user.UserManager;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.ChatPaginator;

import java.util.List;
import java.util.Objects;

public class BrowserMenu extends PaginatedMenu {

    public BrowserMenu(InventoryMapper mapper) {
        super(mapper);
    }

    @Override
    public String getMenuName() {
        return ILib.color("&2Text-Channel browser");
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handle(InventoryClickEvent e) {
        if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.get(), "menu_item"), PersistentDataType.STRING)) {
            switch (Objects.requireNonNull(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.get(), "menu_item"), PersistentDataType.STRING))) {
                case "BROWSER_PREV" -> {
                    if(page == 0) {
                        p.sendMessage(ILib.color("&cThere is no previous page."));
                    } else {
                        page -= 1;
                        super.open();
                    }
                }
                case "BROWSER_NEXT" -> {
                    if(!((index + 1) >= UserManager.getTextChannels().size())) {
                        page += 1;
                        super.open();
                    } else p.sendMessage(ILib.color("&cThere is no next page."));
                }
                case "BROWSER-CLOSE" -> {
                    try {
                        MenuManager.openMenu(BaseMenu.class, p);
                    } catch (MenuManagerException | ManagerSetupException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        if(e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.get(), "tc_item"), PersistentDataType.STRING)) {
            int id = Integer.parseInt(Objects.requireNonNull(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.get(), "tc_item"), PersistentDataType.STRING)).substring(3));
            TextChannel tc = UserManager.getTextChannels().get(id);
            if(!tc.hasPassword()) {
                p.closeInventory();
                UserManager.selectTextChannel(p.getUniqueId().toString(), id);
                p.sendMessage(ILib.color(Uni.PREFIX + "&aChanged Text-Channel to " + tc.getName()));
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            } else {
                p.closeInventory();
                ConversationFactory factory = new ConversationFactory(Main.get())
                        .withEscapeSequence("exit")
                        .withFirstPrompt(new TextChannelPasswordConversation.PasswordPrompt(tc, p))
                        .withLocalEcho(false)
                        .addConversationAbandonedListener(new ConversationAboundedListener())
                        .withTimeout(10);
                factory.buildConversation(p).begin();
            }
        }
    }

    @Override
    public void setMenuItems() {
        setBorderItems();

        /*for (TextChannel tc : UserManager.getTextChannels()) {

        }*/

        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if(index >= UserManager.getTextChannels().size()) break;
            if(UserManager.getTextChannels().get(index) != null) {
                TextChannel tc = UserManager.getTextChannels().get(index);
                List<String> lore = List.of(ILib.color("&8ID: [" + tc.getId() + "]"),
                        ILib.color("&7" + tc.getDescription()), "", ILib.color("&7Click: &aconnect"));
                ItemStack item;
                if(tc.hasPassword()) {
                    item = new ItemBuilder(tc.getIcon())
                            .setName(ILib.color("&a" + tc.getName()) + ILib.color(" &c✖"))
                            .addToPDC("tc_item", "TC_" + tc.getId())
                            .build();
                } else item = new ItemBuilder(tc.getIcon())
                        .setName(ILib.color("&a" + tc.getName()))
                        .addToPDC("tc_item", "TC_" + tc.getId())
                        .build();

                ItemMeta im = item.getItemMeta();
                im.setLore(lore);
                item.setItemMeta(im);
                inventory.addItem(item);

            }
        }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
