package com.mq.wx;

import com.mq.base.GlobalConstants;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class WxBaseController {

    @RequestMapping("/image/{imageRealName}")
    @ResponseBody
    public void execute(HttpServletResponse httpServletResponse, @PathVariable("imageRealName") String imageRealName) {
        try {
            FileInputStream fis = new FileInputStream(new File(GlobalConstants.IMAGE_PATH.concat(imageRealName)));
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                baos.write(b, 0, n);
            }
            fis.close();
            baos.close();
            byte[] img = baos.toByteArray();
            httpServletResponse.setContentType("image/png");
            OutputStream os = httpServletResponse.getOutputStream();
            os.write(img);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
