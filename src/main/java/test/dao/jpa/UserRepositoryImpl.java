package test.dao.jpa;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserRepositoryImpl implements ExtraRepo {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void updateInfo(String name) {
        name = "\'%" + name + "%\'";
        String sql = "update User set id = id + 100 where name like " + name;
        System.out.println("sql: " + sql);
        int res = em.createQuery(sql).executeUpdate();
        System.out.println("update info res: " + res);
    }
}
