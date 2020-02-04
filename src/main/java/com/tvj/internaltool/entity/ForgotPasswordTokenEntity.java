package com.tvj.internaltool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_FORGOT_PASSWORD_TOKEN")
@Setter
@Getter
@NoArgsConstructor
public class ForgotPasswordTokenEntity implements Serializable {

    @Id
    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "token_string")
    private String tokenString;

    @Column(name = "token_expired_date")
    private LocalDateTime tokenExpiredDate;

}