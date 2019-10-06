package study.chat.chatDemo.hello.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String nicName;
    private String message;
}
