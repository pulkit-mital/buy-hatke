package com.pulkit.buyhatke.pojo;

/**
 * @author pulkitmital
 * @date 22-12-2016
 */

public class MessageRow implements Comparable {

    private String address;
    private String body;
    private long timestamp;
    private String date;
    private int threadCount;
    private String searchKey;

    public String getAddress() {
        return address;
    }

    public void setAddress(String pAddress) {
        address = pAddress;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String pBody) {
        body = pBody;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long pTimestamp) {
        timestamp = pTimestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String pDate) {
        date = pDate;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int pThreadCount) {
        threadCount = pThreadCount;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String pSearchKey) {
        searchKey = pSearchKey;
    }

    @Override
    public int compareTo(Object pO) {
        MessageRow messageRow = (MessageRow) pO;
        long diff = this.getTimestamp() - messageRow.getTimestamp();
        return (int) (messageRow.getTimestamp() - this.getTimestamp());
    }
}
