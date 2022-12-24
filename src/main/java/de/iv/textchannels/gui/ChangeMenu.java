package de.iv.textchannels.gui;

import de.iv.ILib;
import de.iv.iutils.exceptions.ManagerSetupException;
import de.iv.iutils.exceptions.MenuManagerException;
import de.iv.iutils.items.ItemBuilder;
import de.iv.iutils.menus.InventoryMapper;
import de.iv.iutils.menus.Menu;
import de.iv.iutils.menus.MenuManager;
import de.iv.textchannels.Main;
import de.iv.textchannels.TextChannel;
import de.iv.textchannels.Uni;
import de.iv.textchannels.user.UserManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ChangeMenu extends Menu {

    public ChangeMenu(InventoryMapper mapper) {
        super(mapper);
    }

    @Override
    public String getMenuName() {
        return ILib.color("&eMenu&7&l/&eChange Text-Channel");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handle(InventoryClickEvent e) {
        if (Objects.requireNonNull(e.getCurrentItem()).hasItemMeta()) {
            ArrayList<TextChannel> textChannels = UserManager.getTextChannels();
            switch (Objects.requireNonNull(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.get(), "menu_item"), PersistentDataType.STRING))) {
                case "TC_UP" -> {
                    TextChannel tc = parseTc((Player) e.getWhoClicked());
                    if (parseTc(p).getId() - 1 >= 0) {
                        TextChannel nTc = textChannels.get(parseTc(p).getId() - 1);
                        if (!nTc.hasPassword()) {
                            UserManager.selectTextChannel(e.getWhoClicked().getUniqueId().toString(), nTc.getId());
                        } else {
                            while(!nTc.hasPassword() | !(nTc.getPerm().equals(""))) {
                                nTc = textChannels.get(nTc.getId()-1);
                            }
                            UserManager.selectTextChannel(p.getUniqueId().toString(), nTc.getId());


                            /*for (int i = nTc.getId(); i > 0; i--) {
                                TextChannel current = textChannels.get(i);
                                if (current.getPassword() != null) {
                                    continue;
                                } else {
                                    UserManager.selectTextChannel(p.getUniqueId().toString(), i);
                                    reloadItems();
                                    p.sendMessage(ILib.color(Uni.PREFIX + "&aChanged Text-Channel to " + nTc.getName()));
                                    p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                    break;
                                }
                            }
                            e.getWhoClicked().sendMessage(ILib.color("&cThere is no previous public Text-Channel."));*/
                        }
                        reloadItems();
                        p.sendMessage(ILib.color(Uni.PREFIX + "&aChanged Text-Channel to " + nTc.getName()));
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    } else {
                        ((Player) e.getWhoClicked()).playSound(e.getWhoClicked(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, -1);
                        e.getWhoClicked().sendMessage(ILib.color("&cThere is no previous Text-Channel."));
                    }
                }
                case "TC_DOWN" -> {
                    TextChannel tc = parseTc((Player) e.getWhoClicked());
                    try {
                        TextChannel nTc = textChannels.get(parseTc(p).getId() + 1);
                        if (!nTc.hasPassword()) {
                            UserManager.selectTextChannel(e.getWhoClicked().getUniqueId().toString(), nTc.getId());
                        } else {
                            while(!nTc.hasPassword() | !(nTc.getPerm().equals(""))) {
                                nTc = textChannels.get(nTc.getId()+1);
                            }
                            UserManager.selectTextChannel(p.getUniqueId().toString(), nTc.getId());


                            /*for (int i = nTc.getId(); i > 0; i--) {
                                TextChannel current = textChannels.get(i);
                                if (current.getPassword() != null) {
                                    continue;
                                } else {
                                    UserManager.selectTextChannel(p.getUniqueId().toString(), i);
                                    reloadItems();
                                    p.sendMessage(ILib.color(Uni.PREFIX + "&aChanged Text-Channel to " + nTc.getName()));
                                    p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                    break;
                                }
                            }
                            e.getWhoClicked().sendMessage(ILib.color("&cThere is no previous public Text-Channel."));*/
                        }
                        reloadItems();
                        p.sendMessage(ILib.color(Uni.PREFIX + "&aChanged Text-Channel to " + nTc.getName()));
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    } catch (IndexOutOfBoundsException ex) {
                        ((Player) e.getWhoClicked()).playSound(e.getWhoClicked(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, -1);
                        e.getWhoClicked().sendMessage(ILib.color("&cThere is no next Text-Channel."));
                    }
                }
                case "MENU_BACK" -> {
                    try {
                        MenuManager.openMenu(BaseMenu.class, p);
                    } catch (MenuManagerException | ManagerSetupException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD)
                .setSkullData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzA0MGZlODM2YTZjMmZiZDJjN2E5YzhlYzZiZTUxNzRmZGRmMWFjMjBmNTVlMzY2MTU2ZmE1ZjcxMmUxMCJ9fX0=")
                .setName(ILib.color("&eprevious"))
                .addToPDC("menu_item", "TC_UP")
                .build());
        inventory.setItem(13, new ItemBuilder(parseTc(p).getIcon()).setName(ILib.color("&a" + parseTc(p).getName() + " &8[" + parseTc(p).getId() + "]"))
                .setLore(ChatPaginator.wordWrap(ILib.color("&7" + parseTc(p).getDescription()), 25))
                .build());
        inventory.setItem(22, new ItemBuilder(Material.PLAYER_HEAD)
                .setSkullData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzNzM0NmQ4YmRhNzhkNTI1ZDE5ZjU0MGE5NWU0ZTc5ZGFlZGE3OTVjYmM1YTEzMjU2MjM2MzEyY2YifX19")
                .setName(ILib.color("&enext"))
                .addToPDC("menu_item", "TC_DOWN")
                .build());
        inventory.setItem(18, new ItemBuilder(Material.PLAYER_HEAD).setName(ILib.color("&eback"))
                .addToPDC("menu_item", "MENU_BACK")
                .setSkullData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")
                .build());

        setFillerGlass();
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    private TextChannel parseTc(Player p) {
        return UserManager.getSelectedTextChannel(p.getUniqueId().toString());
    }

}
