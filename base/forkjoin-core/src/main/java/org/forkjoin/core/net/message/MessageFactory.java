package org.forkjoin.core.net.message;

public interface MessageFactory {
	Message getMessage(int type,int id) throws Exception;
}
