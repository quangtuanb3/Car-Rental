package com.example.case_study_car.Socket;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String senderName;
    private String receiverName;
    private String date;
    private String content;
    private EType type;

    public enum EType {
        CHAT,
        JOIN,
        LEAVE
    }
}
