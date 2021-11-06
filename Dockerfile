#
# Copyright (c) 2019 SAP SE or an SAP affiliate company.
# All rights reserved.
# Please see http://www.sap.com/corporate-en/legal/copyright/index.epx for additional trademark information and
# notices.
#

FROM adoptopenjdk/openjdk15

ARG maintainer=darrell.merryweather@hotmail.com
ARG version=0.0.1-SNAPSHOT

LABEL maintainer=$maintainer app=$app

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=80"

COPY /build/libs/copper-test-$version.jar app.jar

EXPOSE 8080:8080
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
