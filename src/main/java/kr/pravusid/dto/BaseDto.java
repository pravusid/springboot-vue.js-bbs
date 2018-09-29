package kr.pravusid.dto;

import kr.pravusid.domain.BaseEntity;

import java.time.format.DateTimeFormatter;

public abstract class BaseDto {

    private Long id;

    private String regdate;

    private String moddate;

    protected BaseDto() {
    }

    protected <T extends BaseEntity> BaseDto(T entity) {
        this.id = entity.getId();
        this.regdate = entity.getRegdate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.moddate = entity.getModdate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getModdate() {
        return moddate;
    }

}
