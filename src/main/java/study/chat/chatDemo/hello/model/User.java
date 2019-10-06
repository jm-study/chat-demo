package study.chat.chatDemo.hello.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String nicName;
}
