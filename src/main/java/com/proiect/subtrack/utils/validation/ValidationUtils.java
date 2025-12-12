package com.proiect.subtrack.utils.validation;

import com.proiect.subtrack.utils.errors.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Slf4j
@Component
public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^\\d{10}$");

    private static final Pattern NAME_PATTERN =
        Pattern.compile("^[A-Za-z\\s'-]{2,100}$");

    /**
     * Validates phone number - must be exactly 10 digits
     */
    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            log.error("Phone number validation failed: Phone number is null or empty");
            throw new ValidationException("phoneNumber", "Numărul de telefon este obligatoriu");
        }

        String cleaned = phoneNumber.trim();
        if (!PHONE_PATTERN.matcher(cleaned).matches()) {
            log.error("Phone number validation failed: '{}' - must be exactly 10 digits", phoneNumber);
            throw new ValidationException("phoneNumber", "Numărul de telefon trebuie să conțină exact 10 cifre");
        }
        log.debug("Phone number validated successfully: {}", phoneNumber);
    }

    /**
     * Validates email address format
     */
    public void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            log.error("Email validation failed: Email is null or empty");
            throw new ValidationException("email", "Adresa de email este obligatorie");
        }

        String cleaned = email.trim();
        if (!EMAIL_PATTERN.matcher(cleaned).matches()) {
            log.error("Email validation failed: '{}' - invalid format", email);
            throw new ValidationException("email", "Adresa de email nu este validă");
        }
        log.debug("Email validated successfully: {}", email);
    }

    /**
     * Validates name - must contain only letters, spaces, hyphens and apostrophes
     * Minimum 2 characters, maximum 100
     */
    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            log.error("Name validation failed: Name is null or empty");
            throw new ValidationException("name", "Numele este obligatoriu");
        }

        String cleaned = name.trim();
        if (cleaned.length() < 2) {
            log.error("Name validation failed: '{}' - too short (minimum 2 characters)", name);
            throw new ValidationException("name", "Numele trebuie să aibă minim 2 caractere");
        }

        if (cleaned.length() > 100) {
            log.error("Name validation failed: '{}' - too long (maximum 100 characters)", name);
            throw new ValidationException("name", "Numele trebuie să aibă maxim 100 caractere");
        }

        if (!NAME_PATTERN.matcher(cleaned).matches()) {
            log.error("Name validation failed: '{}' - contains invalid characters", name);
            throw new ValidationException("name", "Numele poate conține doar litere, spații, cratimă și apostrof");
        }
        log.debug("Name validated successfully: {}", name);
    }

    /**
     * Validates address - minimum 5 characters, maximum 200
     */
    public void validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            log.error("Address validation failed: Address is null or empty");
            throw new ValidationException("address", "Adresa este obligatorie");
        }

        String cleaned = address.trim();
        if (cleaned.length() < 5) {
            log.error("Address validation failed: '{}' - too short (minimum 5 characters)", address);
            throw new ValidationException("address", "Adresa trebuie să aibă minim 5 caractere");
        }

        if (cleaned.length() > 200) {
            log.error("Address validation failed: '{}' - too long (maximum 200 characters)", address);
            throw new ValidationException("address", "Adresa trebuie să aibă maxim 200 caractere");
        }
        log.debug("Address validated successfully: {}", address);
    }

    /**
     * Validates plan name
     */
    public void validatePlanName(String planName) {
        if (planName == null || planName.trim().isEmpty()) {
            log.error("Plan name validation failed: Plan name is null or empty");
            throw new ValidationException("planName", "Numele planului este obligatoriu");
        }

        String cleaned = planName.trim();
        if (cleaned.length() < 3) {
            log.error("Plan name validation failed: '{}' - too short (minimum 3 characters)", planName);
            throw new ValidationException("planName", "Numele planului trebuie să aibă minim 3 caractere");
        }

        if (cleaned.length() > 50) {
            log.error("Plan name validation failed: '{}' - too long (maximum 50 characters)", planName);
            throw new ValidationException("planName", "Numele planului trebuie să aibă maxim 50 caractere");
        }
        log.debug("Plan name validated successfully: {}", planName);
    }

    /**
     * Validates price - must be positive
     */
    public void validatePrice(Double price) {
        if (price == null) {
            log.error("Price validation failed: Price is null");
            throw new ValidationException("price", "Prețul este obligatoriu");
        }

        if (price <= 0) {
            log.error("Price validation failed: {} - must be positive", price);
            throw new ValidationException("price", "Prețul trebuie să fie pozitiv");
        }

        if (price > 10000) {
            log.error("Price validation failed: {} - exceeds maximum (10000)", price);
            throw new ValidationException("price", "Prețul nu poate depăși 10000");
        }
        log.debug("Price validated successfully: {}", price);
    }

    /**
     * Validates GB amount - must be positive integer
     */
    public void validateGigabytes(Integer gb) {
        if (gb == null) {
            log.error("GB validation failed: GB is null");
            throw new ValidationException("gigabytes", "Cantitatea de GB este obligatorie");
        }

        if (gb <= 0) {
            log.error("GB validation failed: {} - must be positive", gb);
            throw new ValidationException("gigabytes", "Cantitatea de GB trebuie să fie pozitivă");
        }

        if (gb > 1000) {
            log.error("GB validation failed: {} - exceeds maximum (1000)", gb);
            throw new ValidationException("gigabytes", "Cantitatea de GB nu poate depăși 1000");
        }
        log.debug("GB validated successfully: {}", gb);
    }

    /**
     * Validates usage GB amount - must be positive
     */
    public void validateUsageGb(Double usageGb) {
        if (usageGb == null) {
            log.error("Usage GB validation failed: Usage GB is null");
            throw new ValidationException("usageGb", "Consumul de GB este obligatoriu");
        }

        if (usageGb < 0) {
            log.error("Usage GB validation failed: {} - cannot be negative", usageGb);
            throw new ValidationException("usageGb", "Consumul de GB nu poate fi negativ");
        }

        if (usageGb > 1000) {
            log.error("Usage GB validation failed: {} - exceeds maximum (1000)", usageGb);
            throw new ValidationException("usageGb", "Consumul de GB nu poate depăși 1000");
        }
        log.debug("Usage GB validated successfully: {}", usageGb);
    }
}

