package com.example.organizer_module.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplate implements Template {

    VERIFICATION_ORGANIZER("email/Verification");

    private final String name;
}