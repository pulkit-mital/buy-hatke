package com.pulkit.buyhatke.pojo;

import android.util.Log;

/**
 * @author pulkitmital
 * @date 22-12-2016
 */

public class Message implements Comparable {

    private static final String TAG = Message.class.getSimpleName();

    private String address;
    private String body;
    private long timestamp;
    private String date;
    private String type;


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

    public String getDate() {
        return date;
    }

    public void setDate(String pDate) {
        date = pDate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long pTimestamp) {
        timestamp = pTimestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }


    @Override
    public boolean equals(Object obj) {
        Message lModelMessage = (Message) obj;
        return lModelMessage.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Object pO) {
        Message message = (Message) pO;
        long diff = this.getTimestamp() - message.getTimestamp();
        Log.d(TAG, String.valueOf(diff));
        return (int) (this.getTimestamp() - message.getTimestamp());
    }
}