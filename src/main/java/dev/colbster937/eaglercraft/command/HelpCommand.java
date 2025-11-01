package dev.colbster937.eaglercraft.command;

import java.util.ArrayList;

import dev.colbster937.eaglercraft.SingleplayerCommands;
import net.lax1dude.eaglercraft.HString;

public class HelpCommand extends Command {
  public HelpCommand() {
    super("/help", new String[] { "/?" }, "[command]");
  }

  @Override
  public void run(String[] args) {
    ArrayList<Command> commands = SingleplayerCommands.getCommandList();
    if (args.length == 1) {
      SingleplayerCommands.showChat("Commands:");
      for (Command command : commands)
        SingleplayerCommands.showChat(HString.format("- %s %s", command.getCommand(),
            command.getCommandUsage()));
    } else if (args.length == 2) {
      if (!args[1].startsWith("/"))
        args[1] = "/" + args[1];
      for (Command command : commands) {
        if (command.isCommand(args[1])) {
          command.showUsage(args[1]);
          return;
        }
      }
      SingleplayerCommands.showChat(HString.format("Unknown Command %s", args[1]));
    } else {
      this.showUsage(args[0]);
    }
  }
}