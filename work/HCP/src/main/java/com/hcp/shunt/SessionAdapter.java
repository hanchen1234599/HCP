package com.hcp.shunt;

import java.util.concurrent.atomic.AtomicLong;

public class SessionAdapter {
	private static AtomicLong AUTOGETSESSIONID = new AtomicLong( (long)100000 );

	protected Long initSessionID() {
		return AUTOGETSESSIONID.incrementAndGet();
	}
}
