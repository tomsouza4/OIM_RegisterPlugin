# RegisterPlugin Class

## Overview
This Java class automates the registration of plugins within Oracle Identity Management (OIM) environments. It leverages the OIMClient API to interact with the OIM server and perform plugin registration tasks.

## Key Features
- Plugin Registration: Reads a plugin ZIP file and registers it with the OIM server using the PlatformService.registerPlugin method.
- Cache Purging: Clears OIM caches using the PlatformUtilsService.purgeCache method after plugin registration.
- Error Handling: Logs potential exceptions and provides informative messages for troubleshooting.
- Configuration: Uses constants for OIM server details, credentials, and file paths.

## Usage
Update Constants: Modify the constants in the class to reflect your specific OIM environment:
- OIM_HOSTNAME, OIM_PORT, OIM_PROVIDER_URL
- OIM_USERNAME, OIM_PASSWORD
- OIM_CLIENT_HOME, AUTHWL_PATH
- PLUGIN_ZIP_PATH
Compile and Run: Compile the class and execute it using Java.
## Additional Notes
- Dependencies: Requires the OIMClient library and its dependencies.
- Environment: Tested with Oracle Identity Governance 11gR2PS2.
- Customization: Can be adapted for different plugin registration scenarios.

## Example Usage
```sh
java com.wordpress.myidam.RegisterPlugin
```
