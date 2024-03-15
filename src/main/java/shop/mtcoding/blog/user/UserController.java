package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception401;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserRepository userRepository;
    private final HttpSession session;
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO) {
        User sessionUser=userRepository.findByUsernameAndPassword(reqDTO);

        if (sessionUser==null){
            return "redirect:/login-form";
        }

        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO reqDTO) {
        User user=userRepository.save(reqDTO.toEntity());
        session.setAttribute("sessionUser", user); //바로 로그인되게
        return "user/join-form";
    }
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null){
            throw new Exception401("인증되지 않았어요. 로그인 해주세요");
        }

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
