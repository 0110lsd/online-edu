package com.athome.msmservice.service;

import java.util.Map;

public interface MsmService {

    boolean send(Map<String,Object> param, String phoneNum);
}
