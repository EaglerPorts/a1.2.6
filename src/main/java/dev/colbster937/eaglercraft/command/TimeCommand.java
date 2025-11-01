package dev.colbster937.eaglercraft.command;

import java.util.HashMap;
import java.util.Map;

import dev.colbster937.eaglercraft.SingleplayerCommands;
import dev.colbster937.eaglercraft.utils.ScuffedUtils;
import net.lax1dude.eaglercraft.HString;

public class TimeCommand extends Command {
  private static Map<String, String> stringValues;

  public TimeCommand() {
    super("/time", new String[] { "" }, "[ticks]");
  }

  @Override
  public void run(String[] args) {
    if (args.length == 1) {
      SingleplayerCommands.showChat(ScuffedUtils.getFormattedTime(this.mc.theWorld.worldTime));
    } else if (args.length == 2) {
      for (int i = 0; i < stringValues.size(); i++) {
        if (stringValues.containsKey(args[1])) {
          args[1] = stringValues.get(args[1]);
          break;
        }
      }
      try {
        long o = Long.parseLong(args[1]);
        long t = mc.theWorld.worldTime;
        t = (t - (t % 24000)) + o;
        this.mc.theWorld.setWorldTime(t);
        SingleplayerCommands.showChat(HString.format("Set the time to %s", o));
      } catch (Throwable t) {
        this.showCommandError(t);
      }
    } else {
      this.showUsage(args[0]);
    }
  }

  static {
    stringValues = new HashMap<>();
    stringValues.put("day", "1000");
    stringValues.put("noon", "6000");
    stringValues.put("sunset", "23000");
    stringValues.put("night", "13000");
    stringValues.put("midnight", "18000");
    stringValues.put("sunrise", "23000");
  }
}