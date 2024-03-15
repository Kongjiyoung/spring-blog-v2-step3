package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class SaveDTO{
        private String title;
        private String content;

        //DTO를 클라이언트로부터 받아서, PC에 전달받기위해 사용
        public Board toEntity(User user){
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user) //세션유저를 담아줄 곳 //프라이머리키를 담으면 비영속객체를 담아도 ㄱㅊ
                    .build();
        }
    }

    @Data
    public static class UpdateDTO{
        private String title;
        private String content;

    }
}
