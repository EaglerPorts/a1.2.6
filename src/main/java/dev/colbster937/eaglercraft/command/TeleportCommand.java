package dev.colbster937.eaglercraft.command;

import dev.colbster937.eaglercraft.SingleplayerCommands;
import net.lax1dude.eaglercraft.HString;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IChunkProvider;

public class TeleportCommand extends Command {
  public TeleportCommand() {
    super("/teleport", new String[] { "/tp" }, "<x> <y> <z>");
  }

  @Override
  public void run(String[] args) {
    if (args.length == 4) {
      EntityPlayer player = this.mc.thePlayer;
      double[] pos = this.getRelativePos(player, args[1], args[2], args[3]);
      this.tpPos(player, pos);
      SingleplayerCommands
          .showChat(HString.format("Teleported to %.3f, %.3f, %.3f", pos[0], pos[1], pos[2]));
    } else {
      this.showUsage(args[0]);
    }
  }

  private double[] getRelativePos(EntityPlayer player, String x, String y, String z) {
    return new double[] { this.getRelativeCoord(x, player.posX), this.getRelativeCoord(y, player.posY),
        this.getRelativeCoord(z, player.posZ) };
  }

  private void tpPos(EntityPlayer player, double[] pos) {
    if (player.ridingEntity != null)
      player.mountEntity(null);
    player.motionX = 0.0D;
    player.motionY = 0.0D;
    player.motionZ = 0.0D;
    IChunkProvider cp = player.worldObj.getIChunkProvider();
    cp.provideChunk(((int) pos[0]) >> 4, ((int) pos[2]) >> 4);
    player.setPosition(pos[0], pos[1], pos[2]);
    player.prevPosX = pos[0];
    player.prevPosY = pos[1];
    player.prevPosZ = pos[2];
  }
}