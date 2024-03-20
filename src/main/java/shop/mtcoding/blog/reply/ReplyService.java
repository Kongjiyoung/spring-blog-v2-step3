package shop.mtcoding.blog.reply;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserJPARepository;
import shop.mtcoding.blog.user.UserRequest;

import java.util.Optional;

@RequiredArgsConstructor
@Service //IoC 등록
public class ReplyService {
    private final BoardJPARepository boardJPARepository;
    private final ReplyJPARepository replyJPARepository;

    @Transactional
    public Reply 댓글쓰기(ReplyRequest.SaveDTO reqDTO, User sessionUser) {
        Board board = boardJPARepository.findById(reqDTO.getBoardId())
                        .orElseThrow(() -> new Exception404("없는 게시글에 댓글을 작성할 수 없어요"));
        Reply reply = reqDTO.toEntity(sessionUser, board);

        replyJPARepository.save(reply);
        return reply;
    }

    @Transactional
    public void 댓글삭제(int replyId, int sessionUserId) {
        Reply reply = replyJPARepository.findById(replyId)
                .orElseThrow(() -> new Exception404("없는 댓글을 삭제할 수 없어요"));
        if(sessionUserId != reply.getUser().getId()){
            throw new Exception403("댓글을 삭제할 권한이 없습니다");
        }
        replyJPARepository.deleteById(replyId);
    }
}
