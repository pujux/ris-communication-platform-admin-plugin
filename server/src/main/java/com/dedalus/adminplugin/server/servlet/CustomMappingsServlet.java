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
import com.dedalus.adminplugin.DbConnection;
import com.dedalus.adminplugin.shared.Constants;
import com.dedalus.adminplugin.shared.interfaces.CustomMappingsServletInterface;
import com.dedalus.adminplugin.shared.model.CustomMapping;
import com.mirth.connect.client.core.ClientException;
import com.mirth.connect.server.api.MirthServlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@MirthApiProvider(type = ApiProviderType.SERVER_CLASS)
public class CustomMappingsServlet extends MirthServlet implements CustomMappingsServletInterface {

    private Connection dbConnection;

    public CustomMappingsServlet(@Context HttpServletRequest request, @Context SecurityContext sc) {
        super(request, sc, Constants.POINT_NAME);
        dbConnection = DbConnection.getConnection();
    }

    @Override
    public List<CustomMapping> getAllMappings() throws ClientException {
        List<CustomMapping> results = new ArrayList<>();

        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = null;
            String selectSql = "SELECT mapping_name, priority, internal_value_set, external_value_set, default_internal_code_system, default_internal_value, MappingId, MappingValueId, external_code_system, internal_code_system, external_value, internal_value, description FROM V_ALL_MAPPINGS";

            resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                int priority = resultSet.getInt("priority");
                String mappingId = resultSet.getString("MappingId"),
                        mappingValueId = resultSet.getString("MappingValueId"),
                        mappingName = resultSet.getString("mapping_name"),
                        internalValueSet = resultSet.getString("internal_value_set"),
                        externalValueSet = resultSet.getString("external_value_set"),
                        internalCodeSystem = resultSet.getString("internal_code_system"),
                        externalCodeSystem = resultSet.getString("external_code_system"),
                        internalValue = resultSet.getString("internal_value"),
                        externalValue = resultSet.getString("external_value"),
                        description = resultSet.getString("description"),
                        defaultInternalCodeSystem = resultSet.getString("default_internal_code_system"),
                        defaultInternalValue = resultSet.getString("default_internal_value");

                results.add(new CustomMapping(mappingId, mappingValueId, mappingName, priority, internalValueSet,
                        externalValueSet, internalCodeSystem, externalCodeSystem, internalValue, externalValue,
                        Optional.of(description), Optional.of(defaultInternalCodeSystem),
                        Optional.of(defaultInternalValue)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ClientException("getAllMappings failed", e);
        }

        return results;
    }
}
