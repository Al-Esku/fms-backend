package com.fencing.midsouth.fmswebsite.model.validation;

import com.fencing.midsouth.fmswebsite.model.dto.DescriptionForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.fencing.midsouth.fmswebsite.model.validation.ValidationUtils;

import java.util.Objects;
import java.util.Set;

public class DescriptionValidator implements ConstraintValidator<Description, DescriptionForm> {

    private String fieldName;

    private String message;

    private String blankMessage;

    @Override
    public void initialize(Description constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.blankMessage = constraintAnnotation.messageIfBlank();
        this.message = constraintAnnotation.message();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(DescriptionForm descriptionForm, ConstraintValidatorContext context) {
        if (!isLexicalJson(descriptionForm.getDescription())) {
            ValidationUtils.configureContext(context,
                    message,
                    fieldName);
            return false;
        }

        if (isEmptyJson(descriptionForm.getDescription())) {
            ValidationUtils.configureContext(context,
                    blankMessage,
                    fieldName);
            return false;
        }
        return true;
    }

    private boolean isLexicalJson(String text) {
        try {
            JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
            if (jsonObject.keySet().equals(Set.of("root"))) {
                JsonObject root = jsonObject.get("root").getAsJsonObject();
                if (Objects.equals(root.keySet(), Set.of("children", "direction", "format", "indent", "type", "version")) &&
                        root.get("children").isJsonArray() &&
                        (root.get("direction").isJsonNull() || root.get("direction").getAsString() != null) &&
                        (root.get("format").getAsString() != null) &&
                        (root.get("type").getAsString() != null)) {
                    root.get("indent").getAsInt();
                    root.get("version").getAsInt();
                    return true;
                }
            }
        } catch (IllegalStateException | ClassCastException exception) {
            return false;
        }
        return false;
    }

    private boolean isEmptyJson(String text) {
        try {
            JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
            if (jsonObject.keySet().equals(Set.of("root"))) {
                JsonObject root = jsonObject.get("root").getAsJsonObject();
                if (root.get("children").getAsJsonArray().get(0).getAsJsonObject().get("children").getAsJsonArray().isEmpty()) {
                    return true;
                }
            }
        } catch (IllegalStateException | ClassCastException exception) {
            return false;
        }
        return false;
    }
}
