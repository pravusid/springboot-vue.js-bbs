package com.talsist.dto;

import java.time.LocalDateTime;

public abstract class BaseDto {

    private Long id;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }

    public LocalDateTime getModdate() {
        return moddate;
    }

    public void setModdate(LocalDateTime moddate) {
        this.moddate = moddate;
    }

}
