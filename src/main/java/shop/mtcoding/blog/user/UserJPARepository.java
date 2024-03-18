package shop.mtcoding.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.net.InterfaceAddress;

//자동 컴퍼너트 스캔이 된다
//인터페이스는 인터페이스를 상속받을 수 있어서
//CRUD를 제공해준다
public interface UserJPARepository  extends JpaRepository<User, Integer>{

    //카멜로 꺾인 걸 보고 구분해서 쿼리를 만들어준다, 굳이 쓸 필요 없응
    //간단한 쿼리를 만들때만 이렇게 써주기
    //원래는 이렇게 써줌 근데 동적쿼리를 짜줄 때 쿼리만 만들어주는 레파지토리를 만들어준다
    //@Query("select  u from User u where u.username = :username and u.password = :password")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);


}
