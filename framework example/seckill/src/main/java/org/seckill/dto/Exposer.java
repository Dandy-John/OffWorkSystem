package org.seckill.dto;

/**
 * Created by 张栋迪 on 2016/11/28.
 */
public class Exposer {
    private boolean exposed;

    private String md5;

    private long seckilledId;

    private long now;

    private long start;

    private long end;

    public Exposer(boolean exposed, String md5, long seckilledId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckilledId = seckilledId;
    }

    public Exposer(boolean exposed, long seckilledId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckilledId = seckilledId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckilledId) {
        this.exposed = exposed;
        this.seckilledId = seckilledId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckilledId() {
        return seckilledId;
    }

    public void setSeckilledId(long seckilledId) {
        this.seckilledId = seckilledId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckilledId=" + seckilledId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
