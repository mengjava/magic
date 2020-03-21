package com.haoqi.magic.system.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.rigger.core.serializer.LongJsonDeserializer;
import com.haoqi.rigger.core.serializer.LongJsonSerializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2017年11月9日23:33:45
 */
@Data
public class TreeNode {
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    protected Long id;
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    protected Long parentId;
    protected Integer isShow;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
