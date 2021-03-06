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

package org.wso2.carbon.ibus.mediation.cheetah.flow.mediators;

import org.wso2.carbon.ibus.mediation.cheetah.flow.AbstractMediator;
import org.wso2.carbon.ibus.mediation.cheetah.flow.FlowControllerCallback;
import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;

/**
 * Mediator responsible for sending the response from pipeline back to client
 */
public class RespondMediator extends AbstractMediator {

    @Override
    public String getName() {
        return "respond";
    }

    @Override
    public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback)
            throws Exception {

        CarbonCallback parentCallback = carbonCallback;

        // Traverse and find the top most callback coming from transport level
        while (parentCallback instanceof FlowControllerCallback) {
            parentCallback = ((FlowControllerCallback) parentCallback).getParentCallback();
        }

        parentCallback.done(carbonMessage);
        return true;
    }
}
