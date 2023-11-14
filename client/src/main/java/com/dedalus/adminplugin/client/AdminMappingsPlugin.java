/*
 * Copyright 2021 Kaur Palang
 * Copyright 2023 Julian Pufler
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

package com.dedalus.adminplugin.client;

import com.kaurpalang.mirth.annotationsplugin.annotation.MirthClientClass;
import com.dedalus.adminplugin.client.panel.AdminMappingsPanel;
import com.dedalus.adminplugin.shared.Constants;
import com.mirth.connect.client.ui.AbstractSettingsPanel;
import com.mirth.connect.plugins.SettingsPanelPlugin;

@MirthClientClass
public class AdminMappingsPlugin extends SettingsPanelPlugin {

    private AdminMappingsPanel panel;

    public AdminMappingsPlugin(String name) {
        super(name);
    }

    @Override
    public AbstractSettingsPanel getSettingsPanel() {
        return this.panel;
    }

    @Override
    public String getPluginPointName() {
        return String.join(" ", Constants.POINT_NAME, Constants.MAPPINGS_TAB_NAME);
    }

    @Override
    public void start() {
        this.panel = new AdminMappingsPanel();
    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {

    }
}
