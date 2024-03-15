package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor //final에 붙은 생성자를 만들어줘
@Controller //new BooardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {
    private final BoardRepository boardRepository;
    private final HttpSession session;

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardRepository.save(reqDTO.toEntity(sessionUser)); //toentity는 인서트하는 데이터만 사용
        return "redirect:/";
    }


    @GetMapping({ "/" })
    public String index(HttpServletRequest request) {
        List<Board> boardList =boardRepository.findAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }


    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardRepository.findById(id);


        if(board ==null){
            throw new Exception404("게시글을 찾을 수 없습니다");
        }
        request.setAttribute("board", board);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardRepository.findById(id);

        //권한체크
        if(sessionUser.getId() != board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }
        boardRepository.updateById(id, reqDTO.getTitle(), reqDTO.getContent());
        return "redirect:/board/{id}";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardRepository.findById(id);

        //권한체크
        if(sessionUser.getId() != board.getUser().getId()){
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }
        boardRepository.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board =boardRepository.findByIdJoinUser(id);

        boolean isOwner=false;
        if (sessionUser !=null){
            if(sessionUser.getId()==board.getUser().getId()){
                isOwner=true;
            }
        }

        if(board ==null){
            throw new Exception404("게시글을 찾을 수 없습니다");
        }

        request.setAttribute("isOwner", isOwner);
        request.setAttribute("board", board);
        return "board/detail";
    }
}
