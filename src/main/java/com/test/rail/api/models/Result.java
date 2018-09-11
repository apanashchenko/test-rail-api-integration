package com.test.rail.api.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * Created by alpa on 10/25/17
 */
@Data
public class Result {

    @SerializedName("id")
    public Integer id;

    @SerializedName("test_id")
    public Integer testId;

    @SerializedName("status_id")
    public Integer statusId;

    @SerializedName("created_by")
    public Integer createdBy;

    @SerializedName("created_on")
    public Integer createdOn;

    @SerializedName("assignedto_id")
    public Integer assignedtoId;

    @SerializedName("comment")
    public String comment;

    @SerializedName("version")
    public String version;

    @SerializedName("elapsed")
    public String elapsed;

    @SerializedName("defects")
    public String defects;

    @SerializedName("custom_step_results")
    public List<CustomStepResult> customStepResults = null;

}
