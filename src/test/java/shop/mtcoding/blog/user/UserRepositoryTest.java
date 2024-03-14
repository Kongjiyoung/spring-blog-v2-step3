package shop.mtcoding.blog.user;

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
