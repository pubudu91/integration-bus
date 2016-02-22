/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.ibus.mediation.cheetah.config.dsl.internal;


import org.wso2.carbon.ibus.mediation.cheetah.config.ESBConfigHolder;
import org.wso2.carbon.ibus.mediation.cheetah.inbound.InboundEndpoint;
import org.wso2.carbon.ibus.mediation.cheetah.outbound.OutboundEndpoint;
import org.wso2.carbon.ibus.mediation.cheetah.config.dsl.internal.flow.PipelineBuilder;
import org.wso2.carbon.ibus.mediation.cheetah.config.dsl.internal2.inbound.InboundEPBuilder;
import org.wso2.carbon.ibus.mediation.cheetah.config.dsl.internal2.outbound.OutboundEndpointBuilder;

/**
 * A class that used to create entire configuration.Anyone can extend this and overwrite configure method with
 * relevant configuration
 */
public abstract class JavaConfigurationBuilder {


    public abstract IntegrationFlow configure();

    public IntegrationFlow integrationFlow(String name) {
        return new IntegrationFlow(name);
    }

    /**
     * ESB Configuration Builder
     */
    public static class IntegrationFlow {

        private ESBConfigHolder esbConfigHolder;

        public IntegrationFlow(String name) {
            esbConfigHolder = new ESBConfigHolder(name);
        }

        public ESBConfigHolder getEsbConfigHolder() {
            return esbConfigHolder;
        }

        /* For Inbound */
        public InboundEPBuilder inboundEndpoint(String name, InboundEndpoint inboundEndpoint) {
            return InboundEPBuilder.inboundEndpoint(name, esbConfigHolder, this, inboundEndpoint);
        }


        /* For Outbound */
        public OutboundEndpointBuilder outboundEndpoint(OutboundEndpoint outboundEndpoint) {
            return OutboundEndpointBuilder.outboundEndpoint(outboundEndpoint, esbConfigHolder);
        }

        /* For Pipeline */
        public PipelineBuilder pipeline(String name) {
            return PipelineBuilder.pipeline(name, esbConfigHolder);
        }

    }


}
