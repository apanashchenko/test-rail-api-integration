package com.test.rail.api.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * Created by alpa on 10/25/17
 */
@Data
public class Entry {

    @SerializedName("id")
    public String id;

    @SerializedName("suite_id")
    public Integer suiteId;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("include_all")
    public Boolean includeAll;

    @SerializedName("config_ids")
    public List<String> configIds = null;

    @SerializedName("case_ids")
    public List<Integer> caseIds = null;

    @SerializedName("runs")
    public List<Run> runs = null;
}
