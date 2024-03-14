package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor //final에 붙은 생성자를 만들어줘
@Controller //new BooardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {
    private final BoardRepository boardRepository;

    //@Transactional
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, HttpServletRequest request){

        return "redirect:/board/"+id;
    }
    @GetMapping({ "/" })
    public String index(HttpServletRequest request) {
        List<Board> boardList =boardRepository.findAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }

    @PostMapping("/board/save")
    public String save(){


        return "redirect:/";
    }


    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        return "board/update-form";
    }
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){

        return "redirect:/";
    }




    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        Board board =boardRepository.findByIdJoinUser(id);
        request.setAttribute("board", board);
        return "board/detail";
    }
}
