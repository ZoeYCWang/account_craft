package com.quantil.account;

import com.zoe.snow.util.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enums
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/26
 */
public class Enums {
    public enum Status {
        normal(0), locked(1), pending(2);
        private Integer type;

        Status(Integer type) {
            this.type = type;
        }

        private static Map<Integer, Status> map;

        public String getTypeString() {
            if (this.equals(Status.normal))
                return "normal";
            else if (this.equals(Status.locked))
                return "locked";
            else if (this.equals(Status.pending))
                return "pending";
            return "locked";
        }

        public int getType() {
            return this.type;
        }

        public static Status get(int type) {
            if (Validator.isEmpty(type))
                return locked;
            if (map == null) {
                map = new ConcurrentHashMap<>();
                for (Status dataType : Status.values())
                    map.put(dataType.getType(), dataType);
            }
            return map.get(type);
        }
    }
}
