package org.nero.tabby.utils;

import java.io.File;

/**
 * author： nero
 * email: nerosoft@outlook.com
 * data: 16-9-29
 * time: 上午12:28.
 */
public class SystemUtils {

        public int getMaxThread(){
            /**
             * 线程合理大小
             * S = N * U * (1+(WT/ST))
             * N:cpu个数
             * U:目标cpu使用率
             * WT:任务执行线程等待时间
             * ST:任务执行线程使用CPU进行计算的时间
             */
            return Runtime.getRuntime().availableProcessors()*2;
        }
}
