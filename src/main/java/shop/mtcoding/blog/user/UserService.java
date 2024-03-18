package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception400;

import java.util.Optional;

@RequiredArgsConstructor
@Service //IoC 등록
public class UserService {

    private final UserJPARepository userJPARepository;

    @Transactional //JPARepository가 가지고 있어야함 왜냐하면 여러번 커밋해야해서 왜냐하면 하
    public void 회원가입(UserRequest.JoinDTO reqDTO){

        //2. 유저네임 중복검사(서비스 제공) - DB연결이 필요함 예외처리
        Optional<User> userOP=userJPARepository.findByUsername(reqDTO.getUsername());

        if (userOP.isPresent()){
            throw new Exception400("중복된 유저네임입니다");
        }



        userJPARepository.save(reqDTO.toEntity());
    }
}
