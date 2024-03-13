package shop.mtcoding.blog.board;

import lombok.Data;

public class BoardRequest {
    @Data
    public static class updateDTO{ //필드가 똑같아도 화면에 뿌려야할 데이터가 다를 수가 있음
        private String title;
        private String content;
        private String username;
    }
    @Data
    public static class SaveDTO{
        private String title;
        private String content;
        private String username;

        public Board toEntity(){
            return new Board(title, content, username);
        }
    }
}
