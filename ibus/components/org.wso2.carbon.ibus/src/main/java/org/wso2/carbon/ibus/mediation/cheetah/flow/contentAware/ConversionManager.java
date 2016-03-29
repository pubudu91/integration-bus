/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.ibus.mediation.cheetah.config.CheetahConfigRegistry;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext.TypeConverter;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.exceptions.TypeConversionException;
import org.wso2.carbon.ibus.util.ByteBufferBackedInputStream;
import org.wso2.carbon.messaging.CarbonMessage;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConversionManager {
    private static final Logger log = LoggerFactory.getLogger(ConversionManager.class);
    private static ConversionManager manager;

    public static ConversionManager getInstance() {
        if(manager == null)
            manager = new ConversionManager();

        return manager;
    }

    public InputStream convertTo(CarbonMessage cMsg, String sourceType, String targetType) {
        TypeConverter converter = CheetahConfigRegistry.getInstance()
                .getTypeConverterRegistry().lookup(sourceType, targetType);

        if(converter == null)
            return null;

        BlockingQueue<ByteBuffer> contentBuf = aggregateContent(cMsg);
        InputStream input = new ByteBufferBackedInputStream(contentBuf);

        InputStream convertedStream = null;

        try {
            convertedStream = converter.convert(input);
        }
        catch (TypeConversionException e) {
            log.error("Error in converting from: " + sourceType +" to: " + targetType);
        }

        return convertedStream;
    }

    private BlockingQueue<ByteBuffer> aggregateContent(CarbonMessage msg) {

        try {
            //Check whether the message is fully read
            while (!msg.isEndOfMsgAdded()) {
                Thread.sleep(10);
            }
            //Get a clone of content chunk queue from the pipe
            BlockingQueue<ByteBuffer> content = new LinkedBlockingQueue<>(msg.getFullMessageBody());
            return content;
        } catch (Exception e) {
            log.error("Error occurred during conversion from CarbonMessage", e);
        }
        return null;
    }
}