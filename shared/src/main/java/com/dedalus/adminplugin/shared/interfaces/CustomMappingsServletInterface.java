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
import com.dedalus.adminplugin.shared.model.CustomMapping;
import com.mirth.connect.client.core.ClientException;
import com.mirth.connect.client.core.api.BaseServletInterface;
import com.mirth.connect.client.core.api.MirthOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/custom-mappings")
@Tag(name = Constants.POINT_NAME + " | " + Constants.MAPPINGS_TAB_NAME)
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@MirthApiProvider(type = ApiProviderType.SERVLET_INTERFACE)
public interface CustomMappingsServletInterface extends BaseServletInterface {

        @GET
        @Path("/all")
        @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @ApiResponse(responseCode = "200", description = "Custom Mappings Data", content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class)),
                        @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = List.class))
        })
        @MirthOperation(name = "getAllMappings", display = "Retrieve all mappings")
        List<CustomMapping> getAllMappings() throws ClientException;
}
