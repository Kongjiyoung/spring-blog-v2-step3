package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardPersistRepository boardPersistRepository;
    //@Transactional
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.updateDTO reqDTO){
//        Board board = boardPersistRepository.findById(id);
//        board.update(reqDTO); //update를 사용하기 위해서는 트랜지션을 걸어줘야함
        boardPersistRepository.updateById(id, reqDTO);
        return "redirect:/board/"+id;
    }
    @GetMapping({ "/" })
    public String index(HttpServletRequest request) {
        List<Board> boardList=boardPersistRepository.findAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO){
        System.out.println("reqDTO = " + reqDTO);
        boardPersistRepository.save(reqDTO.toEntity());

        return "redirect:/";
    }


    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        Board board=boardPersistRepository.findById(id);
        request.setAttribute("board", board);
        return "board/update-form";
    }
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        boardPersistRepository.deleteById(id);

        return "redirect:/";
    }




    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        Board board = boardPersistRepository.findById(id);
        request.setAttribute("board", board);
        return "board/detail";
    }
}
