package com.dodopal.hessian;

/**
 *
 *
 */
public class HessianSession<V> implements Cloneable {
    private V hessianService;
    private long sessionId;
    private String connectionUrl;

    /**
     * @param hessianService
     * @param sessionId
     * @param connectionUrl
     */
    HessianSession(V hessianService, long sessionId, String connectionUrl) {
        this.hessianService = hessianService;
        this.sessionId = sessionId;
        this.connectionUrl = connectionUrl;
    }

    /**
	 *
	 */
    @SuppressWarnings("unchecked")
    protected Object clone() {
        HessianSession clone;

        try {
            clone = (HessianSession) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }

        clone.sessionId = sessionId;
        clone.connectionUrl = connectionUrl;
        clone.hessianService = null; //只Copy SessionId和ConnectionURL，不Copy Service

        return clone;
    }

    @Override
    public boolean equals(Object target) {
        if (null == target || !(target instanceof HessianService)) {
            return false;
        }

        //以Connection Url作为相等的依据：
        return this.connectionUrl.equals(((HessianSession) target).connectionUrl);
    }

    @Override
    public int hashCode() {
        return this.connectionUrl.hashCode();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append("Service:").append(this.hessianService).append("connUrl:").append(this.connectionUrl).append("session id:").append(this.sessionId);

        return buf.toString();
    }

    /**
     * @return the hessianService
     */
    public V getHessianService() {
        return hessianService;
    }

    /**
     * @return the sessionId
     */
    public long getSessionId() {
        return sessionId;
    }

    /**
     * @return the connectionUrl
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }

    /**
     * @param hessianService the hessianService to set
     */
    public void setHessianService(V hessianService) {
        this.hessianService = hessianService;
    }

}
