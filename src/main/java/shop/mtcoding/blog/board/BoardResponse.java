//package shop.mtcoding.blog.board;
//
//public class BoardResponse {
//
//    public static class DetailId{
//        private int id;
//        private String title;
//        private String content;
//        private UserDTO user;
//
//    }
//}
package shop.mtcoding.blog.board;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BoardResponse {


    //게시글 상세보기 화면
    @Data
    public static class DetailDTO {
        private int id;
        private String title;
        private String content;
        private int userId;
        private String username;
        private List<ReplyDTO> replies =new ArrayList<>();
        private boolean isOwner;

        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId(); //join해서 가져옴
            this.username = board.getUser().getUsername();
            this.isOwner = false;
            if(sessionUser !=null){
                if(sessionUser.getId()==userId) isOwner=true;
            }
            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();

        }

        @Data
        public class ReplyDTO{ //외부에 넣어도 되지만 어떤 DTO인지 한눈에 확인 가능
            private int id;
            private String comment;
            private int userId; //댓글 작성의 아이디
            private String username; //댓글 작성자 이름
            private boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();//lazy loading 해서 가져옴
                this.comment = reply.getComment();
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();//lazy loading 해서 가져옴
                this.isOwner = false;
                if(sessionUser !=null){
                    if(sessionUser.getId()==userId) isOwner=true;
                }
            }
        }


    }

    //게시글 목록보기 화면
    public static class MainDTO{
        private int id;
        private String title;

        public MainDTO(Board board){
            this.id =board.getId();
            this.title= board.getTitle();
        }
    }
}