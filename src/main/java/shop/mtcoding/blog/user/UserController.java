package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpSession session;
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO) {

        try{
            User sessionUser=userRepository.findByUsernameAndPassword(reqDTO);
            session.setAttribute("sessionUser", sessionUser);
        }catch (DataIntegrityViolationException e){
            throw new Exception401("유저네임 혹은 비밀번호가 틀렸습니다");
        }

        return "redirect:/";
    }
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO reqDTO) {
        userService.회원가입(reqDTO);
        return "user/join-form";
    }
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        //조회해서 쓰는게 좋은 이유 : value
        User user=userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);

        return "user/update-form";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user=userRepository.findById(sessionUser.getId());
        User newSessionUser=userRepository.updateById(user.getId(), reqDTO.getPassword(), reqDTO.getEmail());
        session.setAttribute("sessionUser", newSessionUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
