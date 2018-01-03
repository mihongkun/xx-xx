package org.forkjoin.core.net;

import org.forkjoin.core.net.message.MessageFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

import org.forkjoin.core.net.message.coder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SocketServer {
	private static final int DEFAULT_BOSS_THREAD_NUMS = 4;
	private static final int DEFAULT_WORKER_THREAD_NUMS = 8;
	
	public static final SocketServer createMessageServer(int port,final MessageFactory messageFactory,final NetMessageHandler<?> messageHandler){
		return createMessageServer(port, messageFactory, messageHandler, DEFAULT_BOSS_THREAD_NUMS, DEFAULT_WORKER_THREAD_NUMS);
	}
	
	public static final SocketServer createMessageServer(int port,
			final MessageFactory messageFactory,
			final NetMessageHandler<?> messageHandler, int bossThreadNums,
			int workerThreadNums) {
		return new SocketServer(port, new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("messageDecoder", new MessageDecoder(messageFactory, messageHandler));
				p.addLast("messageEncoder", new MessageEncoder());
				
				p.addLast("handler", new MessageChannelHandler(messageHandler));
			}
		}, bossThreadNums, workerThreadNums);
	}

	public static final SocketServer createPacketServer(int port,final NetPacketHandler<?> handler) {
		return createPacketServer(port, handler, DEFAULT_BOSS_THREAD_NUMS, DEFAULT_WORKER_THREAD_NUMS);
	}
	
	public static final SocketServer createPacketServer(int port,final NetPacketHandler<?> handler, int bossThreadNums,	int workerThreadNums) {
		return new SocketServer(port, new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("packetDecoder", new PacketDecoder(handler));
				p.addLast("packetEncoder", new PacketEncoder());
				
				p.addLast("handler", new PacketChannelHandler(handler));
			}
		}, bossThreadNums, workerThreadNums);
	}

	public static final SocketServer createCrcEncryptServer(
			int port,final NetPacketHandler<?> handler, int bossThreadNums,	int workerThreadNums ,
			final int zipSize, final int encryptValue
	) {
		return new SocketServer(port, new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("packetDecoder", new CrcEncryptPacketDecoder(handler));
				p.addLast("packetEncoder", new CrcEncryptPacketEncoder());

				p.addLast("handler", new CrcEncryptChannelHandler(handler,zipSize, encryptValue));
			}
		}, bossThreadNums, workerThreadNums);
	}



	private final int port;
	private final ChannelInitializer<SocketChannel> initializer;
	private final int bossThreadNums;
	private final int workerThreadNums;
	private final static Logger log = LoggerFactory
			.getLogger(SocketServer.class);
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	private SocketServer(int port,
			ChannelInitializer<SocketChannel> initializer, int bossThreadNums,
			int workerThreadNums) {
		this.port = port;
		this.initializer = initializer;
		this.bossThreadNums = bossThreadNums;
		this.workerThreadNums = workerThreadNums;
	}

	public void start() throws Exception {
		if (bossGroup != null) {
			throw new ConnectException("不能重复启动监听");
		}
		log.info("启动端口监听！{}",port);
		bossGroup = new NioEventLoopGroup(bossThreadNums);
		workerGroup = new NioEventLoopGroup(workerThreadNums);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(initializer);
			ChannelFuture f = b.bind(port);
			f.get();
		} finally {
			// bossGroup.shutdownGracefully();
			// workerGroup.shutdownGracefully();
		}
	}

	// public void broadcast(Object obj){
	// for (Channel channel : channelList) {
	// channel.write(obj);
	// }
	// }

	public void close() throws InterruptedException, ExecutionException {
		bossGroup.shutdownGracefully().get();
		workerGroup.shutdownGracefully().get();
	}

	public int getPort() {
		return port;
	}
}
