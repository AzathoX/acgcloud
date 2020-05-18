package org.nrocn.friday.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FridaySession {
    private String userId;

    private String email;

    private boolean support;
}