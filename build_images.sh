#!/bin/bash
# This script is strongly based on the istio bookinfo sample(https://github.com/istio/istio/tree/master/samples/bookinfo).

set -ox errexit

display_usage() {
    echo
    echo "USAGE: ./build-services.sh <version> <prefix> [-h|--help]"
    echo "    -h|--help: Prints usage information"
    echo "    version:   Version of the sample app images (Required)"
    echo "    prefix:    Use the value as the prefix for image names (Required)"
}

if [ "$#" -ne 2 ]; then
  if [ "$1" == "-h" ] || [ "$1" == "--help" ]; then
    display_usage
    exit 0
  else
    echo "Incorrect parameters" "$@"
    display_usage
    exit 1
  fi
fi

VERSION=$1
PREFIX=$2
SCRIPTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

# Docker build variables
ENABLE_MULTIARCH_IMAGES=${ENABLE_MULTIARCH_IMAGES:-"false"}

if [ "${ENABLE_MULTIARCH_IMAGES}" == "true" ]; then
  PLATFORMS="linux/arm64,linux/amd64"
  DOCKER_BUILD_ARGS="docker buildx build --platform ${PLATFORMS} --push"
  # Install QEMU emulators
  docker run --rm --privileged tonistiigi/binfmt --install all
  docker buildx rm multi-builder || :
  docker buildx create --use --name multi-builder --platform ${PLATFORMS}
  docker buildx use multi-builder
else
  DOCKER_BUILD_ARGS="docker build"
fi

# productpage
pushd "$SCRIPTDIR/productpage"
  ${DOCKER_BUILD_ARGS} --pull -t "${PREFIX}/springcloud-bookinfo-productpage-v1:${VERSION}" -t "${PREFIX}/springcloud-bookinfo-productpage-v1:latest" .
popd

# java build the app.
docker run --rm -u root -v "$(pwd)":/home/gradle/project -w /home/gradle/project gradle:7.1-jdk8 gradle clean build

# details
${DOCKER_BUILD_ARGS} --pull -t "${PREFIX}/springcloud-bookinfo-details-v1:${VERSION}" -t "${PREFIX}/springcloud-bookinfo-details-v1:latest" --build-arg 'TARGET=details' .

# ratings
${DOCKER_BUILD_ARGS} --pull -t "${PREFIX}/springcloud-bookinfo-ratings-v1:${VERSION}" -t "${PREFIX}/springcloud-bookinfo-ratings-v1:latest" --build-arg 'TARGET=ratings' .

# reviews
# plain build -- no ratings
${DOCKER_BUILD_ARGS} --pull -t "${PREFIX}/springcloud-bookinfo-reviews-v1:${VERSION}" -t "${PREFIX}/springcloud-bookinfo-reviews-v1:latest" -f reviews/Dockerfile .
# with ratings black stars
${DOCKER_BUILD_ARGS} --pull -t "${PREFIX}/springcloud-bookinfo-reviews-v2:${VERSION}" -t "${PREFIX}/springcloud-bookinfo-reviews-v2:latest"	--build-arg enable_ratings=true -f reviews/Dockerfile .
# with ratings red stars
${DOCKER_BUILD_ARGS} --pull -t "${PREFIX}/springcloud-bookinfo-reviews-v3:${VERSION}" -t "${PREFIX}/springcloud-bookinfo-reviews-v3:latest"	--build-arg enable_ratings=true --build-arg star_color=red  -f reviews/Dockerfile .
