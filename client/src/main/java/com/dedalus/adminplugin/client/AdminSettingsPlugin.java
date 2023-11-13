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
import com.dedalus.adminplugin.client.panel.AdminSettingsPanel;
import com.dedalus.adminplugin.shared.Constants;
import com.mirth.connect.client.ui.AbstractSettingsPanel;
import com.mirth.connect.plugins.SettingsPanelPlugin;

@MirthClientClass
public class AdminSettingsPlugin extends SettingsPanelPlugin {

    private AdminSettingsPanel panel;

    public AdminSettingsPlugin(String name) {
        super(name);
    }

    @Override
    public AbstractSettingsPanel getSettingsPanel() {
        return this.panel;
    }

    @Override
    public String getPluginPointName() {
        return Constants.POINT_NAME;
    }

    @Override
    public void start() {
        this.panel = new AdminSettingsPanel();
    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {

    }
}
