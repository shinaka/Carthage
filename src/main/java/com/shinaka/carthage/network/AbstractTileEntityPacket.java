package com.shinaka.carthage.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by James on 5/26/2014.
 * Updated Abstract packet just for handling basic TileEntity getting/setting
 * When Encoding and Decoding, always call super methods FIRST!
 */
public abstract class AbstractTileEntityPacket extends AbstractPacket
{
    protected int x = 0;
    protected int y = 0;
    protected int z = 0;

    public AbstractTileEntityPacket() { super(); }
    public AbstractTileEntityPacket(int _x, int _y, int _z) { x = _x; y = _y; z = _z; }


    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
    }
}
