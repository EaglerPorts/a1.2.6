package dev.colbster937.eaglercraft.command;

import dev.colbster937.eaglercraft.FormattingCodes;
import dev.colbster937.eaglercraft.SingleplayerCommands;
import net.lax1dude.eaglercraft.HString;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class GiveCommand extends Command {
  public GiveCommand() {
    super("/give", new String[] { "" }, "<id> [count] [damage]");
  }

  @Override
  public void run(String[] args) {
    if (args.length >= 2 && args.length <= 4) {
      try {
        int id = Integer.parseInt(args[1]);
        Block block = Block.blocksList[id];
        Item item = Item.itemsList[id];
        boolean exists = id < 256 ? block != null : item != null;
        if (exists) {
          int num = args.length >= 3 ? Integer.parseInt(args[2]) : 1;
          ItemStack stack = new ItemStack(id, num, args.length == 4 ? Integer.parseInt(args[3]) : 0);
          this.mc.thePlayer.inventory.addItemStackToInventory(stack);
          SingleplayerCommands.showChat(HString.format("Gave the player %s '%s'", num, id));
        } else {
          SingleplayerCommands
              .showChat(FormattingCodes.RED + HString.format("'%s' doesn't exist!", id));
        }
      } catch (Throwable t) {
        this.showCommandError(t);
      }
    } else {
      this.showUsage(args[0]);
    }
  }
}