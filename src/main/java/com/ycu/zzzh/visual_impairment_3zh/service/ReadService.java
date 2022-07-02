package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;

import java.util.List;


public interface ReadService {
    public Msg<List<String>> ReadNews(String nid);
}
