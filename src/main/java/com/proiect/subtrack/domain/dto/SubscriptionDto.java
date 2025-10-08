package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.SubscriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SubscriptionDto {

    private Long subscription_id;

    private PlanDto planDto;

    private UserDto userDto;

    private SubscriptionStatus status;

    private Date created_at;

    private Integer phone_number;

    private Date current_cycle_start;

    private Date current_cycle_stop;

}
