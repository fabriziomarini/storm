/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package org.apache.storm.topology;

import java.util.HashMap;
import java.util.Map;
import org.apache.storm.generated.StreamInfo;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

public class OutputFieldsGetter implements OutputFieldsDeclarer {
    private Map<String, StreamInfo> fields = new HashMap<>();

    @Override
    public void declare(Fields fields) {
        declare(false, fields);
    }

    @Override
    public void declare(boolean direct, Fields fields) {
        declareStream(Utils.DEFAULT_STREAM_ID, direct, fields);
    }

    @Override
    public void declareStream(String streamId, Fields fields) {
        declareStream(streamId, false, fields);
    }

    @Override
    public void declareStream(String streamId, boolean direct, Fields fields) {
        if (null == streamId) {
            throw new IllegalArgumentException("streamId can't be null");
        }
        if (this.fields.containsKey(streamId)) {
            throw new IllegalArgumentException("Fields for " + streamId + " already set");
        }
        this.fields.put(streamId, new StreamInfo(fields.toList(), direct));
    }


    public Map<String, StreamInfo> getFieldsDeclaration() {
        return fields;
    }

}
