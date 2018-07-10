package kr.pravusid.dto;

import kr.pravusid.domain.BaseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public abstract class BaseDto {

    protected Long id;

    protected LocalDateTime regdate;

    protected LocalDateTime moddate;

    protected BaseDto() {
    }

    protected <T extends BaseEntity> BaseDto(T entity) {
        this.id = entity.getId();
        this.regdate = entity.getRegdate();
        this.moddate = entity.getModdate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegdate() {
        return Optional.ofNullable(regdate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))).orElse("");
    }

    public String getModdate() {
        return Optional.ofNullable(moddate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))).orElse("");
    }

}
