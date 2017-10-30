package com.test.rail.api.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by alpa on 10/25/17
 */
@Data
public class CustomStepResult {

    @SerializedName("content")
    public String content;

    @SerializedName("expected")
    public String expected;

    @SerializedName("actual")
    public String actual;

    @SerializedName("status_id")
    public Integer statusId;

}
