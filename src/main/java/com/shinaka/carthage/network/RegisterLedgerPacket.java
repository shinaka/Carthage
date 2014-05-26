package com.shinaka.carthage.network;

import com.shinaka.carthage.blocks.TileEntityRegister;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by James on 5/18/2014.
 */

//Sent from the client ONLY to the server when a text field is updated on the Register GUI
public class RegisterLedgerPacket extends AbstractTileEntityPacket
{
    protected String text1 = "0";
    protected String text2 = "0";
    protected String text3 = "0";
    protected String text4 = "0";

    public RegisterLedgerPacket() { super(); }

    public RegisterLedgerPacket(int _x, int _y, int _z, String _text1, String _text2, String _text3, String _text4)
    {
        super(_x, _y, _z);
        text1 = _text1;
        text2 = _text2;
        text3 = _text3;
        text4 = _text4;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        super.encodeInto(ctx, buffer);
        buffer.writeInt(Integer.parseInt(text1));
        buffer.writeInt(Integer.parseInt(text2));
        buffer.writeInt(Integer.parseInt(text3));
        buffer.writeInt(Integer.parseInt(text4));
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        super.decodeInto(ctx, buffer);
        text1 = Integer.toString(buffer.readInt());
        text2 = Integer.toString(buffer.readInt());
        text3 = Integer.toString(buffer.readInt());
        text4 = Integer.toString(buffer.readInt());
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        TileEntityRegister teRegister = (TileEntityRegister) player.worldObj.getTileEntity(x,y,z);
        if(teRegister != null)
            teRegister.setTextValues(text1, text2, text3, text4);
    }
}
