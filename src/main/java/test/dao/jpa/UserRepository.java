package test.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, ExtraRepo {
    User findByName(String name);

    @Query(value = "select * from user where name = ?1 and email = ?2", nativeQuery = true)
    User complexGet(String name, String email);

    @Query(value = "select * from user where name like %?1%", nativeQuery = true)
    List<User> complexGetV2(String name);

    @Query(value = "select * from user where name like %:username%", nativeQuery = true)
    List<User> complexGetV3(@Param("username") String name);

    @Query(value = "select new test.dao.jpa.ResultJoin(u.name, c.post) from User u join Comment c on u.id = c.uid where u.name = ?1")
    List<ResultJoin> joinTest(String name);

}
