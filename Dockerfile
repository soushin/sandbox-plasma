FROM java:openjdk-8

ENV TZ="JST-9"

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y ca-certificates openssl libc6 libapr1 wget && \
    echo "export LANG=C.UTF-8" > /etc/profile.d/locale.sh && \
    rm -rf /etc/localtime && \
    ln -sf /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 8080 8080

COPY entrypoint.sh /entrypoint.sh
COPY build/libs/plasma-cli.jar /usr/local/plasma-cli/lib/
