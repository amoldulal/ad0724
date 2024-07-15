package com.company.service;

import com.company.model.Tool;
import com.company.model.ToolType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ToolService {
    private static final Map<String, Tool> TOOLS = new HashMap<>();

    static {
        TOOLS.put("CHNS", new Tool("CHNS", ToolType.CHAINSAW, "Stihl", new BigDecimal("1.49"), true, false, true));
        TOOLS.put("LADW", new Tool("LADW", ToolType.LADDER, "Werner", new BigDecimal("1.99"), true, true, false));
        TOOLS.put("JAKD", new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt", new BigDecimal("2.99"), true, false, false));
        TOOLS.put("JAKR", new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid", new BigDecimal("2.99"), true, false, false));
    }

    public Tool findToolByCode(String toolCode) {
        return TOOLS.get(toolCode.toUpperCase());
    }
}
