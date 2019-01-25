package com.example.demo1.entity;

public class Link {
    private Long from;
    private Long to;

    public Link(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Link{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    public Link clone() {
        return  new Link(from ,to);
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }
}
