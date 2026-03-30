package com.proiect.subtrack.domain.dto;

import com.proiect.subtrack.utils.SubscriptionStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionUserViewDto {

    private Long subscriptionId;

    @NotBlank(message = "User name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Included GB is required")
    @PositiveOrZero(message = "Included GB must be positive or zero")
    private Integer includedGb;

    @NotNull(message = "Overage price is required")
    @PositiveOrZero(message = "Overage price must be positive or zero")
    private  Double overagePrice;

    @NotNull(message = "Status is required")
    private SubscriptionStatus status;

    private LocalDate createdAt;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    private LocalDate currentCycleStart;

    private LocalDate currentCycleStop;
}
