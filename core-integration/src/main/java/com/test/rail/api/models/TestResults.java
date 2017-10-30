package com.test.rail.api.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by alpa on 10/25/17
 */
@Data
public class TestResults {

    @SerializedName("status_id")
    public Integer statusId;

    @SerializedName("comment")
    public String comment;

    @SerializedName("version")
    public String version;

    @SerializedName("defects")
    public String defects;

    @SerializedName("elapsed")
    public String elapsed;



}