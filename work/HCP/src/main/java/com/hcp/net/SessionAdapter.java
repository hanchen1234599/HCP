package com.hcp.net;

import java.util.concurrent.atomic.AtomicLong;

public class SessionAdapter {
	private static AtomicLong AUTOGETSESSIONID;

	protected Long initSessionID() {
		return AUTOGETSESSIONID.incrementAndGet();
	}
}
