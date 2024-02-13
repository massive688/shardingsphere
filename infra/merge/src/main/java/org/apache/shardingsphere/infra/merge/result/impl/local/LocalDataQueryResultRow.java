/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.merge.result.impl.local;

import com.google.common.base.Preconditions;
import org.apache.shardingsphere.infra.props.PropertiesConverter;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Local data query result row.
 */
public final class LocalDataQueryResultRow {
    
    private final List<Object> data;
    
    public LocalDataQueryResultRow(final Object... data) {
        this.data = Stream.of(data).map(this::convert).collect(Collectors.toList());
    }
    
    private Object convert(final Object data) {
        if (null == data) {
            return "";
        }
        if (data instanceof Boolean || data instanceof Integer || data instanceof Long) {
            return data.toString();
        }
        if (data instanceof Enum) {
            return ((Enum<?>) data).name();
        }
        if (data instanceof Properties) {
            return PropertiesConverter.convert((Properties) data);
        }
        return data;
    }
    
    /**
     * Get data from cell.
     * 
     * @param columnIndex column index
     * @return data from cell
     */
    public Object getCell(final int columnIndex) {
        Preconditions.checkArgument(columnIndex > 0 && columnIndex < data.size() + 1);
        return data.get(columnIndex - 1);
    }
}
