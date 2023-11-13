# Communication Platform Admin Plugin

This plugin is used to add a settings panel to the Mirth Administrator GUI which enables the configuration of custom settings and custom mappings from inside the Mirth Administrator GUI.

## Installation

1. [Install Java](https://www.javatpoint.com/javafx-how-to-install-java)
2. [Install Maven](https://www.javatpoint.com/how-to-install-maven)
3. Run `git clone https://github.com/DedalusDIIT/ris-communication-platform-admin-plugin`
4. Navigate to `ris-communication-platform-version-plugin/`
5. [Create](https://github.com/kpalang/mirth-plugin-guide#---signing-and-publishing) a `keystore.jks` file in `certificate/`
6. Run `mvn install` to install dependencies to local cache
7. Run `mvn clean package` to verify the build works
8. You can now find the plugins `.zip` archive in `distribution/target`

## Usage

You can find the custom panel under "Settings", it should be the rightmost tab. Once opened, you should be presented with options to configure settings and mappings.

## License

Copyright 2021 Kaur Palang<br/>
Copyright 2023 Julian Pufler

Licensed under the Apache License, Version 2.0 (the "License");<br/>
You may not use this code except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br/>
See the License for the specific language governing permissions and
limitations under the License.
