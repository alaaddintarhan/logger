FROM icr.io/appcafe/open-liberty:full-java11-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
  org.opencontainers.image.authors="AlaaddinTarhan" \
  org.opencontainers.image.vendor="demo" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/alaaddintarhan/logger/tree/master/demo" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="demo \
  version="$VERSION-$REVISION" \
  summary="The system microservice from the MicroProfile Reactive Messaging guide" \
  description="This image contains the system microservice running with the Open Liberty runtime."

COPY --chown=1001:0 src/main/liberty/config /config/
COPY --chown=1001:0 target/system.war /config/apps

EXPOSE 9083 9446

# It is recommended to run the configure.sh when build image for production.
# RUN configure.sh