package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor //final에 붙은 생성자를 만들어줘
@Controller //new BooardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {
    private final HttpSession session;
    private final BoardService boardService;

    // TODO : 글목록조회 API 필요 -> @GetMapping("/")
    // TODO : 글조회 API 필요 -> @GetMapping("/api/boards/{id}")
    // TODO : 글상세보기 API 필요 -> @GetMapping("/api/boards/{id}/detail")
    @PostMapping("/api/boards")
    public String save(BoardRequest.SaveDTO reqDTO){

        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글쓰기(reqDTO, sessionUser); //toentity는 인서트하는 데이터만 사용
        return "redirect:/";
    }


    @PutMapping("/api/boards/{id}")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");

        return "redirect:/board/{id}";
    }

    @DeleteMapping("/api/boards/{id}")
    public String delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return "redirect:/";
    }


}
