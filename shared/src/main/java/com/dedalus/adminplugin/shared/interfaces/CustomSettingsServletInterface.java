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

package com.dedalus.adminplugin.shared.interfaces;

import com.kaurpalang.mirth.annotationsplugin.annotation.MirthApiProvider;
import com.kaurpalang.mirth.annotationsplugin.type.ApiProviderType;
import com.dedalus.adminplugin.shared.Constants;
import com.dedalus.adminplugin.shared.model.CustomSetting;
import com.mirth.connect.client.core.ClientException;
import com.mirth.connect.client.core.api.BaseServletInterface;
import com.mirth.connect.client.core.api.MirthOperation;
import com.mirth.connect.client.core.api.Param;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/custom-settings")
@Tag(name = Constants.POINT_NAME + " | " + Constants.SETTINGS_TAB_NAME)
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@MirthApiProvider(type = ApiProviderType.SERVLET_INTERFACE)
public interface CustomSettingsServletInterface extends BaseServletInterface {

        @GET
        @Path("/all")
        @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @MirthOperation(name = "getAllSettings", display = "Retrieve all settings")
        @ApiResponse(responseCode = "200", description = "Successful", content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class)),
                        @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = List.class)),

        })
        List<CustomSetting> getAllSettings() throws ClientException;

        @POST
        @Path("/upsert")
        @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @MirthOperation(name = "upsertSetting", display = "Upsert a setting")
        @ApiResponse(responseCode = "200", description = "Successful")
        boolean upsertSetting( // workaround because requestBody version doesnt seem to work
                        @Param("id") @Parameter(description = "The settings ID", required = false) @QueryParam("id") String id,
                        @Param("key") @Parameter(description = "The settings key", required = true) @QueryParam("key") String key,
                        @Param("value") @Parameter(description = "The settings value", required = true) @QueryParam("value") String value,
                        @Param("description") @Parameter(description = "The settings description", required = false) @QueryParam("description") String description)
                        // @Param("setting")
                        // @Parameter(description
                        // =
                        // "The
                        // setting
                        // to
                        // be
                        // upserted",
                        // required = true) @RequestBody(description = "The setting to upsert", required
                        // = true, content = { @Content(mediaType = MediaType.APPLICATION_JSON, schema =
                        // @Schema(implementation = CustomSetting.class)) }) CustomSetting setting
                        throws ClientException;

        @DELETE
        @Path("/delete/{id}")
        @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @MirthOperation(name = "deleteSetting", display = "Delete a setting")
        @ApiResponse(responseCode = "200", description = "Successful")
        boolean deleteSetting(
                        @Param("id") @Parameter(description = "The ID of the setting to be deleted", required = true) @PathParam("id") String id)
                        throws ClientException;

}
