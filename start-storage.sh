#!/usr/bin/env bash
gcloud beta emulators datastore start --project=appengine-test --host-port=localhost:8081 --consistency 1.0 --no-store-on-disk