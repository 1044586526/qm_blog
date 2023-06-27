package com.qm.service;


import com.qm.common.ResponseResult;
import com.qm.dto.LoginDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public interface LoginService {

    Map<String, String> getCode(HttpServletResponse response) throws IOException;


    ResponseResult login(LoginDTO vo);
}
