package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Hello;
import study.querydsl.entity.QHello;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
//@Commit // test 에 사용한 data 지우지 않고 남김
class QuerydslApplicationTests {

    @Autowired
    EntityManager em;

    @Test
    void contextLoads() { // queryDsl 써보자
        Hello hello = new Hello();
        em.persist(hello); // entitymanager 저장

        JPAQueryFactory query = new JPAQueryFactory(em); // queryDsl 쓰려면 일단 em Factory 만들고
//        QHello qHello = new QHello("hello"); // alias 필요하다 > 왜..? "hello" ... 의미 없나 ?
        QHello qHello = QHello.hello;

        Hello result = query
                .selectFrom(qHello)
                .fetchOne();

        assertThat(result).isEqualTo(hello);
        assertThat(result.getId()).isEqualTo(hello.getId());
    }
}
