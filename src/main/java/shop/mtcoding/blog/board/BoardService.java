package shop.mtcoding.blog.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service //IoC 등록
public class BoardService {
    public final BoardJPARepository boardJPARepository;

    // board, isOwner
    public Board 글상세보기(int boardId, User sessionUser) {
        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        boolean isBoardOwner = false;
        if(sessionUser != null){
            if(sessionUser.getId() == board.getUser().getId()){
                isBoardOwner = true;
            }
        }

        board.setBoardOwner(isBoardOwner);

        board.getReplies().forEach(reply -> {
            boolean isReplyOwner=false;
            if(sessionUser != null) {
                if (reply.getUser().getId() == sessionUser.getId()) {
                    isReplyOwner = true;
                }
            }
            System.out.println("isReplyOwner = " + isReplyOwner);
            reply.setReplyOwner(isReplyOwner);
        });

        return board;
    }
    public Board 글조회(int boardId){//id를 정확하게 적어주기
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));


       return board;
    }


    @Transactional
    public void 글수정(int boardId, int sessionUserId, BoardRequest.UpdateDTO reqDTO){//id를 정확하게 적어주기
        //1. 조회 및 예외처리
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        //2. 권한 처리
        if(sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }
        //3. 글 수정
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
    }

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO reqDTO, User sessionUser){
        boardJPARepository.save(reqDTO.toEntity(sessionUser));
    }

    public void 글삭제(int boardId, int sessionUserId) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));


        //권한체크
        if(sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }
        boardJPARepository.deleteById(boardId);
    }

    public List<Board> 글목록조회() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return boardJPARepository.findAll(sort);
    }

    //board, isOwner

}
