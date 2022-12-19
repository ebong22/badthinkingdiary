package ebong.badthinkingdiary;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    /*
     * LAZY loading 관련 오류 해결을 위한 설정
     * ERROR : No serializer found for class hibernateLazyInitializer
     * CAUSE : LAZY Loading 설정해둔 것 때문 이었음 ex) DiaryPrivate의 @ManyToOne(fetch = FetchType.LAZY) Member
     *          Jackson으로 DiaryPrivate entity를 Serialize할 때 LAZY설정으로 된 비어있는 객체를 직렬화하려고 해서 발생하는 문제
     *          => LAZY Loading 시 필요할 때 실제 데이터를 가져옴
     *          => 그 전에는 실제 Member가 아닌 proxy로 감싸져 있음 (hibernateLazyInitializer)
     *          => 그래서 실제 Member 객체가 아닌것을 serialize하려고 하는 것이기 때문에 문제가 생기는 것
     */
    @Bean
    Hibernate5Module hibernate5Module() {
        // Lazy loading 강제 데이터 로딩 (실제 해당 객체를 조회해와서 반환함 )
//        return new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);

        // Lazy loading 없이 원하는 엔티티 조회 (LazyLoading 데이터는 null로 표시됨)
        return new Hibernate5Module();
    }
}
