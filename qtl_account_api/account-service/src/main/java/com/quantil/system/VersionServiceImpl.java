package com.quantil.system;

import com.zoe.snow.context.request.Request;
import com.zoe.snow.crud.Result;
import com.zoe.snow.log.Logger;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * VersionServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/8/24
 */
@Service("rdc.system.version.service")
public class VersionServiceImpl implements VersionService {
    @Autowired
    private Request request;

    @Override
    public Result get(String token) {
        return Result.reply(() -> {
            String path = request.getRealPath("/");
            File file = new File(path + "/VERSION.json");
            BufferedReader reader = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                while ((tempString = reader.readLine()) != null) {
                    line++;
                    stringBuilder.append(tempString);
                }
                reader.close();
            } catch (IOException e) {
                Logger.error(e,"read the version from version.json occur some problem.");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        Logger.error(e1,"when close the read occur some problem.");
                    }
                }
            }
            return JSONObject.fromObject(stringBuilder.toString());
        });
    }
}
