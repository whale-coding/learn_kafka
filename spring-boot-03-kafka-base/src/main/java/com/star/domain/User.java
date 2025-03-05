package com.star.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname: User
 * @Date: 2025/3/3 21:57
 * @Author: 聂建强
 * @Description:
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    private int id;

    private String phone;

    private Date birthDay;
}
