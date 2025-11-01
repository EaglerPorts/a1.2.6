package dev.colbster937.eaglercraft;

import java.util.ArrayList;

import dev.colbster937.eaglercraft.command.Command;
import dev.colbster937.eaglercraft.command.GiveCommand;
import dev.colbster937.eaglercraft.command.HelpCommand;
import dev.colbster937.eaglercraft.command.SummonCommand;
import dev.colbster937.eaglercraft.command.TeleportCommand;
import dev.colbster937.eaglercraft.command.TimeCommand;
import net.lax1dude.eaglercraft.HString;
import net.lax1dude.eaglercraft.profile.EaglerProfile;
import net.minecraft.client.Minecraft;

public class SingleplayerCommands {
  private static final Minecraft mc;
  private static final ArrayList<Command> commands;

  public static void processCommand(String commandString) {
    if (!commandString.startsWith("/"))
      commandString = "/" + commandString;
    String[] args = commandString.trim().replaceAll(" +", " ").split(" ");
    for (Command command : commands) {
      if (command.isCommand(args[0])) {
        command.run(args);
        return;
      }
    }
    showChat(HString.format("Unknown Command %s", args[0]));
  }

	public static boolean processRaw(String textString) {
		if (!mc.isMultiplayerWorld() && textString.startsWith("/")) {
			processCommand(textString);
			return true;
		}
		return false;
	}

  public static void showChat(String msg) {
    mc.ingameGUI.addChatMessage(msg);
  }

  public static void showDummyChat(String msg) {
    showChat("<" + EaglerProfile.getName() + "> " + msg);
  }

  public static ArrayList<Command> getCommandList() {
    return new ArrayList<Command>(commands);
  }

  static {
    mc = Minecraft.getMinecraft();
    commands = new ArrayList<>();
    commands.add(new HelpCommand());
    commands.add(new TeleportCommand());
    commands.add(new GiveCommand());
    commands.add(new SummonCommand());
    commands.add(new TimeCommand());
  }
}
