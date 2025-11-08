package dev.colbster937.eaglercraft.command;

import dev.colbster937.eaglercraft.FormattingCodes;
import dev.colbster937.eaglercraft.SingleplayerCommands;
import net.lax1dude.eaglercraft.HString;
import net.minecraft.client.Minecraft;

public class Command {
  protected final Minecraft mc;

  private final String cmd;
  private final String[] aliases;
  private final String usage;

  public Command(String cmd, String[] aliases, String usage) {
    this.mc = Minecraft.getMinecraft();
    this.cmd = cmd;
    this.aliases = aliases;
    this.usage = usage;
  }

  public boolean isCommand(String commandString) {
    String command = commandString.split(" ")[0];
    if (command.equals(this.cmd))
      return true;
    for (String alias : this.aliases)
      if (command.equals(alias))
        return true;
    return false;
  }

  public void run(String[] args) {
  }

  protected void showUsage(String cmd) {
    SingleplayerCommands
        .showChat(HString.format("Usage: %s %s", cmd, this.usage));
  }

  protected void showUsage() {
    this.showUsage(this.cmd);
  }

  protected void showCommandError(Throwable t) {
    SingleplayerCommands.showChat(
        FormattingCodes.DARK_RED + HString.format("An error occurred: %s", t.getMessage()));
  }

  protected double getRelativeCoord(Object offset, double coord) {
    if (!(offset instanceof String))
      return coord;
    String s = ((String) offset).trim();
    if (s.equals("~"))
      return coord;
    if (s.startsWith("~")) {
      try {
        return coord + (s.length() == 1 ? 0 : Double.parseDouble(s.substring(1)));
      } catch (Throwable e) {
        return coord;
      }
    }
    try {
      return Double.parseDouble(s);
    } catch (Throwable t) {
      return coord;
    }
  }

  public String getCommand() {
    return this.cmd;
  }

  public String[] getCommandAliases() {
    return this.aliases;
  }

  public String getCommandUsage() {
    return this.usage;
  }
}