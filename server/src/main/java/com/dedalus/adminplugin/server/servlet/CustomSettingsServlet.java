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

package com.dedalus.adminplugin.server.servlet;

import com.kaurpalang.mirth.annotationsplugin.annotation.MirthApiProvider;
import com.kaurpalang.mirth.annotationsplugin.type.ApiProviderType;
import com.dedalus.adminplugin.server.DbConnection;
import com.dedalus.adminplugin.shared.Constants;
import com.dedalus.adminplugin.shared.interfaces.CustomSettingsServletInterface;
import com.dedalus.adminplugin.shared.model.CustomSetting;
import com.mirth.connect.client.core.ClientException;
import com.mirth.connect.server.api.MirthServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@MirthApiProvider(type = ApiProviderType.SERVER_CLASS)
public class CustomSettingsServlet extends MirthServlet implements CustomSettingsServletInterface {

    private Connection dbConnection;

    public CustomSettingsServlet(@Context HttpServletRequest request, @Context SecurityContext sc) {
        super(request, sc, Constants.POINT_NAME);
        dbConnection = DbConnection.getConnection();
    }

    @Override
    public List<CustomSetting> getAllSettings() throws ClientException {
        List<CustomSetting> results = new ArrayList<>();
        System.out.println("getAllSettings");

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = null;
            String selectSql = "SELECT ID, SETTING_KEY, SETTING_VALUE, DESCRIPTION FROM CUSTOM_SETTINGS";

            resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                String id = resultSet.getString("ID"),
                        key = resultSet.getString("SETTING_KEY"),
                        value = resultSet.getString("SETTING_VALUE"),
                        description = resultSet.getString("DESCRIPTION");

                results.add(new CustomSetting(id, key, value, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ClientException("getAllSettings failed", e);
        }

        return results;
    }

    @Override
    public boolean upsertSetting(String id, String key, String value, String description) throws ClientException {
        CustomSetting setting = new CustomSetting(id, key, value, description);
        System.out.println("Upsert: " + setting.getKey());
        boolean isUpsert = false;

        if (setting.getId() != null && setting.getId().length() > 0) {
            try (PreparedStatement statement = dbConnection
                    .prepareStatement("SELECT * FROM CUSTOM_SETTINGS WHERE ID = ?")) {

                statement.setObject(1, UUID.fromString(setting.getId())); // ID is UUID in postgres
                ResultSet resultSet = statement.executeQuery();
                isUpsert = resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ClientException("upsertSetting failed", e);
            }
        }

        try (PreparedStatement statement = dbConnection
                .prepareStatement(
                        isUpsert ? "UPDATE CUSTOM_SETTINGS SET SETTING_KEY = ?, SETTING_VALUE = ?, DESCRIPTION = ? WHERE ID = ?"
                                : "INSERT INTO CUSTOM_SETTINGS (SETTING_KEY, SETTING_VALUE, DESCRIPTION) VALUES (?, ?, ?)")) {

            statement.setString(1, setting.getKey());
            statement.setString(2, setting.getValue());
            statement.setString(3, setting.getDescription());
            if (isUpsert)
                statement.setObject(4, UUID.fromString(setting.getId())); // ID is UUID in postgres

            return statement.executeUpdate() == 1; // check if row has been updated
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ClientException("upsertSetting failed", e);
        }
    }

    @Override
    public boolean deleteSetting(String id) throws ClientException {
        System.out.println("Delete: " + id);
        try (PreparedStatement statement = dbConnection
                .prepareStatement(
                        "DELETE FROM CUSTOM_SETTINGS WHERE ID = ?")) {

            statement.setObject(1, UUID.fromString(id));

            return statement.executeUpdate() == 1; // check if row has been updated
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ClientException("deleteSetting failed", e);
        }
    }
}
