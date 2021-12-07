package com.isbd.model;

public class Pageable {
    private final int limit;
    private final int offset;

    public Pageable(int page, int size) {
        if (page < 0) {
            this.limit = Integer.MAX_VALUE;
            this.offset = 0;
        } else {
            this.limit = size;
            this.offset = page * size;
        }
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
