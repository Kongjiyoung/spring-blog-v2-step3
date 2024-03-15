package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class) //IoC 등록코드
@DataJpaTest //Datasource(connetion pool), Entitymanage 퍼시던트 컨텐트 매니저 //필요한거만 메모리에 띄우겠다 단위테스트
public class UserRepositoryTest {
    @Autowired //DI 레파지토리에 쓸 수 있지만 계속 써야함
    private UserRepository userRepository; //new가 되는게 아님

    @Autowired
    private EntityManager em;

    @Test
    public void updateById_test(){
        // given
        int id=1;
        String password="123456";
        String email="ssar12@nate.com";
        // when
        userRepository.updateById(id, password, email);
        em.flush(); //실제코드는 작성할 필요가 없다. 트랜잭션이 종료되기 때문에
        // then
        System.out.println("updateById_test : " + userRepository.findById(1));

    }
    @Test
    public void findById_test(){
        // given
        int id=1;

        // when
        userRepository.findById(id);

        // then
    }
    @Test
    public void findByUsername_test(){
        // given
        UserRequest.LoginDTO reqDTO =new UserRequest.LoginDTO();
        reqDTO.setUsername("ssar");
        reqDTO.setPassword("1234");

        // when
        User user = userRepository.findByUsernameAndPassword(reqDTO);

        // then
        Assertions.assertThat(user.getUsername()).isEqualTo("ssa");
    }
}
