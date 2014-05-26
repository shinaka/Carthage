package com.shinaka.carthage.network;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by James on 5/26/2014.
 */
public class LedgerStatusPacket extends AbstractTileEntityPacket
{
    protected boolean bHasLedger;

    public LedgerStatusPacket() { super(); }

    public LedgerStatusPacket(int _x, int _y, int _z, boolean _hasLedger)
    {
        super(_x, _y, _z);
        bHasLedger = _hasLedger;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        super.encodeInto(ctx, buffer);
        buffer.writeBoolean(bHasLedger);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        super.decodeInto(ctx, buffer);
        bHasLedger = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        TileEntityTradingPost tePost = (TileEntityTradingPost) player.worldObj.getTileEntity(x,y,z);
        if(tePost != null)
            tePost.SetHasLedger(bHasLedger);
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
