package ebong.badthinkingdiary.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class AbstractEntity {

    @CreatedDate
    private LocalDateTime registerDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * @TODO 시큐리티 : @LastModifiedBy 적용 관련하여 처리해주어야 하는 부분이 있는 것 같음. 시큐리티 할 때 다시 확인 (AuditorAware)
     */
//    @LastModifiedBy
    private String updateId;

}
