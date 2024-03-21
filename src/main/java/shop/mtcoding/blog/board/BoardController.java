package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor //final에 붙은 생성자를 만들어줘
@Controller //new BooardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {
    private final HttpSession session;
    private final BoardService boardService;

    // TODO : 글목록조회 API 필요 -> @GetMapping("/")
    @GetMapping("/")
    public ResponseEntity<?> main(){
        List<BoardResponse.MainDTO> boardList = boardService.글목록조회();
        return ResponseEntity.ok(new ApiUtil(boardList));
    }
    // TODO : 글상세보기 API 필요 -> @GetMapping("/api/boards/{id}/detail")
    @GetMapping("/boards/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO board1= boardService.글상세보기(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil(board1));
    }
    // TODO : 글조회 API 필요 -> @GetMapping("/api/boards/{id}")

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> fileOne(@PathVariable Integer id){
        Board board = boardService.글조회(id);
        return ResponseEntity.ok(new ApiUtil(board));
    }

    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO reqDTO){

        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board=boardService.글쓰기(reqDTO, sessionUser); //toentity는 인서트하는 데이터만 사용
        return ResponseEntity.ok(new ApiUtil(board));
    }




    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BoardRequest.UpdateDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글수정(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil(board));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }


}
