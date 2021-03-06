package com.fan.mysql.dbsync;


import com.fan.mysql.event.impl.FormatDescriptionEvent;
import com.fan.mysql.event.impl.TableMapEvent;
import com.fan.mysql.util.MySQLConstants;

import java.util.HashMap;
import java.util.Map;

public class BinlogContext {

    private boolean semiSync;
    private boolean needReply;
    private String timeZone;
    private String binlogFileName;
    private long binlogPosition;
    private FormatDescriptionEvent formatDescription;
    private final Map<Long, TableMapEvent> tableMapEvents = new HashMap<>();

    public BinlogContext() {
        this.formatDescription = new FormatDescriptionEvent(MySQLConstants.BINLOG_CHECKSUM_ALG_OFF);
    }

    public BinlogContext(int binlogChecksum) {
        this.formatDescription = new FormatDescriptionEvent(binlogChecksum);
    }

    public boolean isSemiSync() {
        return semiSync;
    }

    public void setSemiSync(boolean semiSync) {
        this.semiSync = semiSync;
    }

    public boolean isNeedReply() {
        return needReply;
    }

    public void setNeedReply(boolean needReply) {
        this.needReply = needReply;
    }

    public String getBinlogFileName() {
        return binlogFileName;
    }

    public void setBinlogFileName(String binlogFileName) {
        this.binlogFileName = binlogFileName;
    }

    public long getBinlogPosition() {
        return binlogPosition;
    }

    public void setBinlogPosition(long binlogPosition) {
        this.binlogPosition = binlogPosition;
    }

    public FormatDescriptionEvent getFormatDescription() {
        return formatDescription;
    }

    public void setFormatDescription(FormatDescriptionEvent formatDescription) {
        this.formatDescription = formatDescription;
    }

    public Map<Long, TableMapEvent> getTableMapEvents() {
        return tableMapEvents;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

}
