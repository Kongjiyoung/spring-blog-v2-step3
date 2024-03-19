package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final HttpSession session;
    private final ReplyService replyService;

    @PostMapping("/reply/save")
    public String save(ReplyRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글쓰기(reqDTO, sessionUser);
        return "redirect:/board/"+reqDTO.getBoardId();
    }

    @PostMapping("/board/{boardId}/reply/{replyId}/save")
    public String delete(@PathVariable int replyId, @PathVariable int boardId){
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(replyId, sessionUser.getId());
        return "redirect:/board/"+boardId;
    }
}
