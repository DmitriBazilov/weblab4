package com.dmitri.backend.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="shots")
public class HitDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private UserDTO user;

    @Basic
    @Column(name = "x", nullable = false)
    private double x;
    @Basic
    @Column(name = "y", nullable = false)
    private double y;
    @Basic
    @Column(name = "r", nullable = false)
    private double r;
    @Basic
    @Column(name = "hit", nullable = false)
    private boolean hit;
    @Basic
    @Column(name = "currentTime", nullable = false)
    private Timestamp currentTime;
    @Basic
    @Column(name = "executeTime", nullable = false)
    private long executeTime;

    public HitDTO() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public String toString() {
        return "HitDTO{" +
                "id=" + id +
                ", user=" + user +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", hit=" + hit +
                ", currentTime=" + currentTime +
                ", executeTime=" + executeTime +
                '}';
    }
}
