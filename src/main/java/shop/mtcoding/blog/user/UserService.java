package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.BoardJPARepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service //IoC 등록
public class UserService {

    private final UserJPARepository userJPARepository;

    public User 회원수정(int id, UserRequest.UpdateDTO reqDTO){
        User user= userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다") );

        user.setPassword(reqDTO.getPassword());
        user.setEmail(reqDTO.getEmail());
        return user;
    }
    public User 회원조회(int id){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));

        return user;
    }

    public User 로그인(UserRequest.LoginDTO reqDTO){
        User sessionUser = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다")); //ssar 1234를 넣으면 값이 들어감, 조회를 했는데 값이 null이면 throw를 날리겠다

        return sessionUser;
    }
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
