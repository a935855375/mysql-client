package com.fan.mysql.event.impl;


import com.fan.mysql.column.EventColumn;

import java.util.BitSet;

public class RowData {

    private EventColumn[] beforeColumns;
    private EventColumn[] afterColumns;
    private BitSet beforeBit;
    private BitSet afterBit;

    public EventColumn[] getBeforeColumns() {
        return beforeColumns;
    }

    public void setBeforeColumns(EventColumn[] beforeColumns) {
        this.beforeColumns = beforeColumns;
    }

    public EventColumn[] getAfterColumns() {
        return afterColumns;
    }

    public void setAfterColumns(EventColumn[] afterColumns) {
        this.afterColumns = afterColumns;
    }

    public BitSet getBeforeBit() {
        return beforeBit;
    }

    public void setBeforeBit(BitSet beforeBit) {
        this.beforeBit = beforeBit;
    }

    public BitSet getAfterBit() {
        return afterBit;
    }

    public void setAfterBit(BitSet afterBit) {
        this.afterBit = afterBit;
    }

}
