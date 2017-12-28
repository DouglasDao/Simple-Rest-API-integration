package com.fact.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Dell on 02-10-2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class RandomFactResponse extends BaseResponse {
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
