package com.example.user_module.template;


public interface TemplateEngine {

    String compile(String template, Object model);

    default String compile(Template template, Object model) {
        return compile(template.getName(), model);
    }
}