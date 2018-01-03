package org.forkjoin.core.net;

import org.forkjoin.core.net.message.Packet;
import io.netty.channel.ChannelHandlerContext;

@SuppressWarnings({"rawtypes","unchecked"})
public class PacketChannelHandler extends ChannelHandler<NetPacketHandler<?>,Packet> {
	private NetPacketHandler<?> messageHandler;
	public PacketChannelHandler(NetPacketHandler<?> messageHandler) {
		super(messageHandler);
		this.messageHandler = messageHandler;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg)
			throws Exception {
//		if(log.isDebugEnabled()){
//			log.debug("收到消息!{}", msg);
//		}
		Session session = ctx.channel().attr(SESSION).get();
		msg.setSession(session);
		messageHandler.onPacket(msg);
	}
    
}
