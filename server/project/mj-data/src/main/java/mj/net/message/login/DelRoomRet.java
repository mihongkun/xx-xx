package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 解散房间结果
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DelRoomRet extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 3;
	
	private boolean result;
	
	public DelRoomRet(){
		
	}
	
	public DelRoomRet(boolean result){
		this.result = result;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		result = in.readBoolean();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeBoolean(getResult());
	}

	public boolean getResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}

	
	@Override
	public String toString() {
		return "DelRoomRet [result=" + result + ", ]";
	}
	
	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public final int getMessageId() {
		return ID;
	}
}
