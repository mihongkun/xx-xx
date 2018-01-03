/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.forkjoin.core.net.message.coder;

import org.forkjoin.core.io.MarkCompressOutput;
import org.forkjoin.core.net.message.Message;
import org.forkjoin.core.net.message.MessageException;
import org.forkjoin.core.net.message.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.forkjoin.core.io.Output;

public class MessageEncoder extends MessageToByteEncoder<Message> {
	
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
    	try{
    		int startIdx = out.writerIndex();
    		//MessageProtocol.LENGTH_BYTE_NUMS 修改后必须修改这个代码
        	out.writeMedium(0);
        	out.writeByte(MessageProtocol.TYPE_NORMAL);
        	
//        	ByteBufOutputStream bout = new ByteBufOutputStream(out);
        	Output o = MarkCompressOutput.create(out);
        	
        	o.writeInt(msg.getMessageType());
        	o.writeInt(msg.getMessageId());
        	msg.encode(o);
        	o.close();
        	
        	int endIdx = out.writerIndex();
        	int len = endIdx - startIdx - MessageProtocol.HEAD_LENGTH;
        	if(len > MessageProtocol.MESSAGE_MAX){
        		throw  MessageException.newLengthException(len);
        	}
        	//MessageProtocol.LENGTH_BYTE_NUMS 修改后必须修改这个代码
        	out.setMedium(startIdx, len);
    	}catch(Throwable t){
			ctx.fireExceptionCaught(t);
		}
    }
}
