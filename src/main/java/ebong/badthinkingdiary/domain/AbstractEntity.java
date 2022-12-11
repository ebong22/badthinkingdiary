package ebong.badthinkingdiary.domain;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class AbstractEntity {

    private LocalDateTime registerDate;
    private String registerId;
    private LocalDateTime updateDate;
    private String updateId;

}
