package com.dodopal.common.facade;

/**
 * @author lifeng@dodopal.com
 */

public abstract class AbstractFacade implements BaseFacade {
	private String trackId;

	@Override
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTrackId() {
		return trackId;
	}
}