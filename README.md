AppEngine-Spine3 test project
=============================

# Build

In order to build project just run:
```bash
gradlew clean install
```

# Start application

1. Install [`gcloud`][gcloud]
2. Update `gcloud` components and install `Cloud Datastore Emulator`, `gcloud app Java Extensions`
and `gcloud Beta Commands` if they are not yet installed:
   ```bash
   gcloud components update
   gcloud components install app-engine-java cloud-datastore-emulator beta
   ```
3. Initialize `gcloud` with `gcloud init`
4. Set application-default authentication:
   ```bash
   gcloud auth application-default login
   ```
5. Set `CLOUD_SDK_HOME` environment variable
  
   For example, it could point to `C:\Program Files (x86)\Google\Cloud SDK\google-cloud-sdk\` on Windows.
6. Start local development server:
   ```bash
   gradlew appengineRun
   ```

In order to stop development server run:
```bash
gradlew appengineStop
```

[gcloud]: https://cloud.google.com/sdk/docs/#install_the_latest_cloud_tools_version_cloudsdk_current_version