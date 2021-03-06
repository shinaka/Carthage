package com.shinaka.carthage.network;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by James on 5/25/2014.
 */
public class TradingPostPacket extends AbstractTileEntityPacket
{

    protected int itemCost = 0;

    public TradingPostPacket() { super(); }

    public TradingPostPacket( int _x, int _y, int _z, int _itemCost )
    {
        super(_x, _y, _z);
        itemCost = _itemCost;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        super.encodeInto(ctx, buffer);
        buffer.writeInt(itemCost);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        super.decodeInto(ctx, buffer);
        itemCost = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        TileEntityTradingPost tePost = (TileEntityTradingPost) player.worldObj.getTileEntity(x,y,z);
        if(tePost != null)
            tePost.setItemCost(itemCost);
    }
}
