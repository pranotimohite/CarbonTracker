package com.carbontrack.carbontrack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Entity
@Data
@Getter
@Setter
@Table(name="ACTIVITY")
public class Activity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "activity_seq_gen")
    @SequenceGenerator(
            name = "activity_seq_gen",
            sequenceName = "activity_seq",
            allocationSize = 1
    )
    private Long id;
    private String type;
    private double value;
    private String unit;
    private double co2Emitted;
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        Activity other = (Activity)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getValue(), other.getValue()) != 0) {
            return false;
        }
        if (Double.compare(this.getCo2Emitted(), other.getCo2Emitted()) != 0) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$unit = this.getUnit();
        String other$unit = other.getUnit();
        if (this$unit == null ? other$unit != null : !this$unit.equals(other$unit)) {
            return false;
        }
        LocalDateTime this$timestamp = this.getTimestamp();
        LocalDateTime other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof Activity;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $value = Double.doubleToLongBits(this.getValue());
        result = result * 59 + (int)($value >>> 32 ^ $value);
        long $co2Emitted = Double.doubleToLongBits(this.getCo2Emitted());
        result = result * 59 + (int)($co2Emitted >>> 32 ^ $co2Emitted);
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $unit = this.getUnit();
        result = result * 59 + ($unit == null ? 43 : $unit.hashCode());
        LocalDateTime $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "Activity(id=" + this.getId() + ", type=" + this.getType() + ", value=" + this.getValue() + ", unit=" + this.getUnit() + ", co2Emitted=" + this.getCo2Emitted() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }

    @Generated
    public Activity() {
    }

    public Activity(ActivityBuilder activityBuilder)
    {
        this.id = activityBuilder.id;
        this.type = activityBuilder.type;
        this.value = activityBuilder.value;
        this.unit = activityBuilder.unit;
        this.co2Emitted = activityBuilder.co2Emitted;
        this.timestamp = activityBuilder.timestamp;
    }

    @Generated
    public Activity(Long id, String type, double value, String unit, double co2Emitted, LocalDateTime timestamp) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.co2Emitted = co2Emitted;
        this.timestamp = timestamp;
    }

    @Generated
    public static class ActivityBuilder {
        @Generated
        private Long id;
        @Generated
        private String type;
        @Generated
        private double value;
        @Generated
        private String unit;
        @Generated
        private double co2Emitted;
        @Generated
        private LocalDateTime timestamp;

        @Generated
        ActivityBuilder() {
        }

        @Generated
        public ActivityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public ActivityBuilder type(String type) {
            this.type = type;
            return this;
        }

        @Generated
        public ActivityBuilder value(double value) {
            this.value = value;
            return this;
        }

        @Generated
        public ActivityBuilder unit(String unit) {
            this.unit = unit;
            return this;
        }

        @Generated
        public ActivityBuilder co2Emitted(double co2Emitted) {
            this.co2Emitted = co2Emitted;
            return this;
        }

        @Generated
        public ActivityBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Generated
        public Activity build() {
//            return new Activity(this.id, this.type, this.value, this.unit, this.co2Emitted, this.timestamp);
            return new Activity(this);
        }

        @Generated
        public String toString() {
            return "Activity.ActivityBuilder(id=" + this.id + ", type=" + this.type + ", value=" + this.value + ", unit=" + this.unit + ", co2Emitted=" + this.co2Emitted + ", timestamp=" + String.valueOf(this.timestamp) + ")";
        }
    }
}
