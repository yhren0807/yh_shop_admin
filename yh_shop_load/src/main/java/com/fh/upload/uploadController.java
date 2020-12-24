package com.fh.upload;


import com.fh.utils.returns.CommonReturn;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("uploadController")
public class uploadController {
    @PostMapping("upload")
    public CommonReturn uploadCategory(@RequestParam("file") MultipartFile file) throws IOException {
        String ums_icon = uploadImg.aliyunFile("ums_icon", file);
        return CommonReturn.success(ums_icon);
    }

}
