package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor //final에 붙은 생성자를 만들어줘
@Controller //new BooardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {
    private final HttpSession session;
    private final BoardService boardService;
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글쓰기(reqDTO, sessionUser); //toentity는 인서트하는 데이터만 사용
        return "redirect:/";
    }

    @GetMapping({ "/" })
    public String index(HttpServletRequest request) {
        List<Board> boardList=boardService.글목록조회();
        request.setAttribute("boardList", boardList);
        return "index";
    }


    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글조회(id);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");

        return "redirect:/board/{id}";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return "redirect:/";
    }


//    @GetMapping("/board/{id}")
//    public String detail(@PathVariable Integer id, HttpServletRequest request) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        boardService.글상세보기(id, sessionUser);
//
//        request.setAttribute("isOwner", isOwner);
//        request.setAttribute("board", board);
//        return "board/detail";
//    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글상세보기(id, sessionUser);



        request.setAttribute("board", board);
        System.out.println("서버 사이드 랜더링 직전에는 Board 상태이다");
        return "board/detail";
    }
}
