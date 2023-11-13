/*
 * Copyright 2021 Kaur Palang
 * Copyright 2023 Kaur Palang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dedalus.adminplugin.server;

import com.dedalus.adminplugin.DbConnection;
import com.dedalus.adminplugin.shared.Constants;
import com.kaurpalang.mirth.annotationsplugin.annotation.MirthServerClass;
import com.mirth.connect.model.ExtensionPermission;
import com.mirth.connect.plugins.ServicePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@MirthServerClass
public class AdminPlugin implements ServicePlugin {

    @Override
    public void init(Properties properties) {
    }

    @Override
    public void update(Properties properties) {
    }

    @Override
    public Properties getDefaultProperties() {
        return new Properties();
    }

    @Override
    public ExtensionPermission[] getExtensionPermissions() {
        return null;
    }

    @Override
    public Map<String, Object> getObjectsForSwaggerExamples() {
        return new HashMap<>();
    }

    @Override
    public String getPluginPointName() {
        return Constants.POINT_NAME;
    }

    @Override
    public void start() {
        System.out.println("Starting " + Constants.POINT_NAME);
    }

    @Override
    public void stop() {
        System.out.println("Stopping " + Constants.POINT_NAME);
        DbConnection.closeConnection();
    }
}
