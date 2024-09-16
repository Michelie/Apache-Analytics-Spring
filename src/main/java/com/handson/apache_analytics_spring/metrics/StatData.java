package com.handson.apache_analytics_spring.metrics;

import java.util.Objects;

public class StatData {
    String title;
    String aggr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatData statData = (StatData) o;
        return Objects.equals(title, statData.title) && Objects.equals(aggr, statData.aggr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, aggr);
    }

    @Override
    public String toString() {
        return "StatData{" +
                "title='" + title + '\'' +
                ", aggr='" + aggr + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAggr() {
        return aggr;
    }

    public void setAggr(String aggr) {
        this.aggr = aggr;
    }

    public static final class StatDataBuilder {
        private String title;
        private String aggr;

        private StatDataBuilder() {
        }

        public static StatDataBuilder aStatData() {
            return new StatDataBuilder();
        }

        public StatDataBuilder title(String title) {
            this.title = title;
            return this;
        }

        public StatDataBuilder aggr(String aggr) {
            this.aggr = aggr;
            return this;
        }

        public StatData build() {
            StatData statData = new StatData();
            statData.setTitle(title);
            statData.setAggr(aggr);
            return statData;
        }
    }
}

