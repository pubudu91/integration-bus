/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.ibus.mediation.cheetah.outbound.protocol.http;

import org.wso2.carbon.ibus.ServiceContextHolder;
import org.wso2.carbon.ibus.mediation.cheetah.config.CheetahConfigRegistry;
import org.wso2.carbon.ibus.mediation.cheetah.flow.FlowControllerCallback;
import org.wso2.carbon.ibus.mediation.cheetah.outbound.OutboundEndpoint;
import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.Constants;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * HTTP Outbound Endpoint
 */
public class HTTPOutboundEndpoint extends OutboundEndpoint {

    private String uri;

    @Override
    public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback)
            throws Exception {
        processRequest(carbonMessage);

        ServiceContextHolder.getInstance().getSender().send(carbonMessage, carbonCallback);
        return false;
    }

    private void setCarbonHeadersToBackendRequest(CarbonMessage request, String host, int port,
                                                  String urls) {

        if (request != null) {

            request.setProperty(Constants.HOST, host);
            request.setProperty(Constants.PORT, port);


            request.setProperty(Constants.TO, urls);


            if (port != 80) {
                request.getHeaders().put(Constants.HTTP_HOST, host + ":" + port);
            } else {
                request.getHeaders().put(Constants.HTTP_HOST, host);
            }

        }
    }

    private void processRequest(CarbonMessage carbonMessage) throws MalformedURLException {
        URL url = new URL(uri);
        String host = url.getHost();
        int port = (url.getPort() == -1) ? 80 : url.getPort();
        String urls = url.getPath();
        setCarbonHeadersToBackendRequest(carbonMessage, host, port, urls);

    }

    public HTTPOutboundEndpoint(String name, String uri) {
        super(name);
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }


}
