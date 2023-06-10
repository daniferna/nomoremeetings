package com.dfernandezaller.model;

public record TimeDistribution(double busy, double tentative, double ooo) {

    public TimeDistribution(double busy, double tentative, double ooo) {
        this.busy = Math.round(busy * 100d) / 100d;
        this.tentative = Math.round(tentative * 100d) / 100d;
        this.ooo = Math.round(ooo * 100d) / 100d;
    }

}
