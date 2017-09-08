package com.talsist.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Date regdate;

    @LastModifiedDate
    private Date moddate;

    public Long getId() {
        return id;
    }

    public String getRegdate() {
        if (regdate == null) {
            return "";
        }
        return LocalDateTime
                .ofInstant(regdate.toInstant(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public Date getModdate() {
        return moddate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractEntity other = (AbstractEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", regdate=" + regdate + ", moddate=" + moddate + "]";
    }

}
