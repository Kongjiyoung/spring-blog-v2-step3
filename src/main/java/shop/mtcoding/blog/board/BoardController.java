package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardNativeRepository boardNativeRepository;

    @PostMapping("/board/save")
    public String save(String username, String title, String content){
        boardNativeRepository.save(title, content, username);

        return "redirect:/";
    }
    @GetMapping({ "/" })
    public String index(HttpServletRequest request) {//세션에 담지않고 request에 담으면 한번 쓰고 버릴 수 있음

        List<Board> boardList=boardNativeRepository.findAll();
        request.setAttribute("boardList", boardList);
        return "index"; // /요청했으면 request객체 디비정보를 담았음 index를 찾아서 응답해줌 리퀘스트 디스페처 다른 헬스장으로 갈거
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/1")
    public String detail() {
        return "board/detail";
    }
}
